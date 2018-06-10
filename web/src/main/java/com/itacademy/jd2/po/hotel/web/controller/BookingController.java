package com.itacademy.jd2.po.hotel.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.service.IBookingService;
import com.itacademy.jd2.po.hotel.web.converter.BookingFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.BookingFromGuestBookingDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.BookingToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.BookingDTO;
import com.itacademy.jd2.po.hotel.web.dto.GuestBookingDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.Slider;
import com.itacademy.jd2.po.hotel.web.dto.search.BookingSearchDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/booking")
public class BookingController extends AbstractController<BookingDTO, BookingFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = BookingController.class.getSimpleName() + "_SEACH_DTO";

    @Autowired
    private IBookingService bookingService;
    @Autowired
    private BookingFromDTOConverter fromDTOConverter;
    @Autowired
    private BookingToDTOConverter toDTOConverter;
    @Autowired
    private BookingFromGuestBookingDTOConverter bookingFromGuestBookingDTOConverter;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) BookingSearchDTO searchDTO,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<BookingDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDTO = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }

        final BookingFilter filter = applyFilter(searchDTO);

        listDTO.setTotalCount(bookingService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        filter.setFetchRoom(true);
        filter.setFetchUserAccount(true);
        filter.setFetchBookingStatus(true);
        final List<IBooking> entities = bookingService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        loadAllCommonItems(models);
        models.put(Slider.SESSION_SLIDER_NAME, getSlider(searchDTO));
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDTO);
        return new ModelAndView("booking.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {
        final Map<String, Object> hashMap = new HashMap<>();
        final IBooking newBooking = bookingService.createEntity();
        final BookingDTO dto = toDTOConverter.apply(newBooking);
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("booking.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final BookingDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadAllCommonItems(hashMap);
            return new ModelAndView("booking.edit", hashMap);
        } else {
            final IBooking entity = fromDTOConverter.apply(formModel);
            try {
                bookingService.save(entity);
            } catch (OptimisticLockException e) {
                // get latest version
                final BookingDTO newFormModel = toDTOConverter.apply(bookingService.getFullInfo(formModel.getId()));
                hashMap.put("error", "OptimisticLockException");
                hashMap.put("formModel", newFormModel);
                loadAllCommonItems(hashMap);
                return new ModelAndView("booking.edit", hashMap);
            } catch (PersistenceException e) {
                loadAllCommonItems(hashMap);
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("booking.edit", hashMap);
            }
            return "redirect:/booking";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        bookingService.delete(id);
        return "redirect:/booking";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IBooking dbModel = bookingService.getFullInfo(id);
        final BookingDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadAllCommonItems(hashMap);
        return new ModelAndView("booking.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final BookingDTO dto = toDTOConverter.apply(bookingService.getFullInfo(id));
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadAllCommonItems(hashMap);
        return new ModelAndView("booking.edit", hashMap);
    }

    @RequestMapping(value = "/mybooking", method = RequestMethod.GET)
    public ModelAndView myBooking(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<BookingDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final BookingFilter filter = new BookingFilter();
        filter.setFetchUserAccount(true);
        filter.setFetchRoom(true);
        filter.setFetchBookingStatus(true);
        filter.setUserAccountId(AuthHelper.getLoggedUserId());
        listDTO.setTotalCount(bookingService.getCount(filter));
        applySortAndOrder2Filter(listDTO, filter);

        final List<IBooking> entities = bookingService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("booking.mybooking", models);
    }

    @RequestMapping(value = "/newbooking", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView showForm(@ModelAttribute("formModel") final GuestBookingDTO guestBookingDTO) {
        final Map<String, Object> hashMap = new HashMap<>();
        loadCommonFormRooms(hashMap);
        hashMap.put("formModel", guestBookingDTO);
        return new ModelAndView("newbooking.edit", hashMap);
    }

    @RequestMapping(value = "/newbooking/add", method = RequestMethod.POST)
    public Object save(@Valid @ModelAttribute("formModel") final GuestBookingDTO guestBookingDTO,
            final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", guestBookingDTO);
            loadCommonFormRooms(hashMap);
            return new ModelAndView("newbooking.edit", hashMap);
        } else {
            final IBooking entity = bookingFromGuestBookingDTOConverter.apply(guestBookingDTO);
            try {
                Integer countDays = daysBetween(entity.getCheckIn(), entity.getCheckOut());
                entity.setRoomPrice(countDays * entity.getRoomPrice());
                bookingService.save(entity);
            } catch (PersistenceException e) {
                loadCommonFormRooms(hashMap);
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("newbooking.edit", hashMap);
            }
            return "redirect:/booking/mybooking";
        }
    }

    public int daysBetween(final Date d1, final Date d2) {
        return (int) (Math.abs((d2.getTime() - d1.getTime())) / (1000 * 60 * 60 * 24));
    }

    private Slider getSlider(final BookingSearchDTO searchDTO) {
        Double max = bookingService.getMaxPrice();
        if (max == null) {
            max = 100.00;
        }
        Double valueMin = searchDTO.getPriceMin();
        if (valueMin == null) {
            valueMin = 0.00;
        }
        Double valueMax = searchDTO.getPriceMax();
        if (valueMax == null) {
            valueMax = max;
        }
        return new Slider(valueMin, valueMax, max);
    }

    private BookingFilter applyFilter(final BookingSearchDTO searchDTO) {
        final BookingFilter filter = new BookingFilter();

        if (searchDTO.getRoomNumberId() != null) {
            filter.setRoomNumberId(searchDTO.getRoomNumberId());
        }

        if (searchDTO.getUserAccountId() != null) {
            filter.setUserAccountId(searchDTO.getUserAccountId());
        }

        if (searchDTO.getBookingStatusId() != null) {
            filter.setBookingStatusId(searchDTO.getBookingStatusId());
        }

        if (searchDTO.getCheckIn() != null) {
            filter.setCheckIn(searchDTO.getCheckIn());
        }

        if (searchDTO.getCheckOut() != null) {
            filter.setCheckOut(searchDTO.getCheckOut());
        }

        if (searchDTO.getAccomodation() != null) {
            filter.setAccomodation(searchDTO.getAccomodation());
        }
        if (searchDTO.getView() != null) {
            filter.setView(searchDTO.getView());
        }
        if (searchDTO.getPriceMin() != null) {
            filter.setPriceMin(searchDTO.getPriceMin());
        }
        if (searchDTO.getPriceMax() != null) {
            filter.setPriceMax(searchDTO.getPriceMax());
        }
        return filter;
    }

    private void loadAllCommonItems(final Map<String, Object> hashMap) {
        loadCommonFormRooms(hashMap);
        loadCommonFormGuestAndEmployeesAccounts(hashMap);
        loadCommonFormBookingStatuses(hashMap);
    }

    private BookingSearchDTO getSearchDTO(final HttpServletRequest req) {
        BookingSearchDTO searchDTO = (BookingSearchDTO) req.getSession().getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new BookingSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }
}
