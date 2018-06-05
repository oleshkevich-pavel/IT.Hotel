package com.itacademy.jd2.po.hotel.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.service.IBookingStatusService;
import com.itacademy.jd2.po.hotel.web.converter.BookingStatusFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.BookingStatusToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.BookingStatusDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/bookingstatus")
public class BookingStatusController extends AbstractController<BookingStatusDTO, BookingStatusFilter> {

    @Autowired
    private IBookingStatusService bookingStatusService;
    @Autowired
    private BookingStatusFromDTOConverter fromDTOConverter;

    @Autowired
    private BookingStatusToDTOConverter toDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<BookingStatusDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final BookingStatusFilter filter = new BookingStatusFilter();
        applySortAndOrder2Filter(listDTO, filter);

        final List<IBookingStatus> entities = bookingStatusService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(bookingStatusService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("bookingstatus.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", new BookingStatusDTO());
        hashMap.put("colors", getDefaultColors());
        return new ModelAndView("bookingstatus.edit", hashMap);
    }

    private List<Integer> getDefaultColors() {
        List<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            colors.add(127);
        }
        return colors;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final BookingStatusDTO dto = toDTOConverter.apply(bookingStatusService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("colors", getListRGB(dto));

        return new ModelAndView("bookingstatus.edit", hashMap);
    }

    private List<Integer> getListRGB(final BookingStatusDTO dto) {
        String color = dto.getColor();
        String[] colorsString = color.substring(4, color.length() - 1).trim().split(",");
        List<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < colorsString.length; i++) {
            colors.add(Integer.parseInt(colorsString[i]));
        }
        return colors;
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final BookingStatusDTO formModel,
            final BindingResult result) {
        if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            hashMap.put("colors", getListRGB(formModel));
            return new ModelAndView("bookingstatus.edit", hashMap);
        } else {
            final IBookingStatus entity = fromDTOConverter.apply(formModel);
            try {
                bookingStatusService.save(entity);
            } catch (PersistenceException e) {
                final Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("error", "PersistenceException");
                return new ModelAndView("bookingstatus.edit", hashMap);
            }
            return "redirect:/bookingstatus";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        bookingStatusService.delete(id);
        return "redirect:/bookingstatus";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IBookingStatus dbModel = bookingStatusService.getFullInfo(id);
        final BookingStatusDTO dto = toDTOConverter.apply(dbModel);
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);

        return new ModelAndView("bookingstatus.edit", hashMap);
    }
}
