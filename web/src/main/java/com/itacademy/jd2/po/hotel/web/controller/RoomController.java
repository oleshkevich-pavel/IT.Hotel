package com.itacademy.jd2.po.hotel.web.controller;

import java.util.Arrays;
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

import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.RoomType;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;
import com.itacademy.jd2.po.hotel.service.IBookingService;
import com.itacademy.jd2.po.hotel.service.IPhotoLinkService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.web.converter.BookingToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.PhotoLinkToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.RoomFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.RoomToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.BookingDTO;
import com.itacademy.jd2.po.hotel.web.dto.PhotoLinkDTO;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;

@Controller
@RequestMapping(value = "/room")
public class RoomController extends AbstractController<RoomDTO, RoomFilter> {

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IPhotoLinkService photoLinkService;
    @Autowired
    private RoomFromDTOConverter fromDTOConverter;
    @Autowired
    private BookingToDTOConverter bookingToDTOConverter;
    @Autowired
    private RoomToDTOConverter toDTOConverter;
    @Autowired
    private PhotoLinkToDTOConverter photoLinkToDTOConverter;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {

        final ListDTO<RoomDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        final RoomFilter filter = new RoomFilter();
        applySortAndOrder2Filter(listDTO, filter);

        final List<IRoom> entities = roomService.find(filter);
        listDTO.setList(entities.stream().map(toDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(roomService.getCount(filter));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        return new ModelAndView("room.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        final RoomDTO dto = new RoomDTO();
        hashMap.put("formModel", dto);
        loadAllComboboxes(hashMap);
        return new ModelAndView("room.edit", hashMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("formModel") final RoomDTO formModel, final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            hashMap.put("formModel", formModel);
            loadAllComboboxes(hashMap);
            return new ModelAndView("room.edit", hashMap);
        } else {
            final IRoom entity = fromDTOConverter.apply(formModel);
            try {
                roomService.save(entity);
            } catch (PersistenceException e) {
                hashMap.put("error", "PersistenceException");
                loadAllComboboxes(hashMap);
                return new ModelAndView("room.edit", hashMap);
            }
            return "redirect:/room";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        roomService.delete(id);
        return "redirect:/room";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IRoom dbModel = roomService.getFullInfo(id);
        final RoomDTO dto = toDTOConverter.apply(dbModel);

        final BookingDTO bookingDTO = bookingToDTOConverter.apply(bookingService.createEntity());
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("bookingModel", bookingDTO);
        hashMap.put("readonly", true);

        ListDTO<PhotoLinkDTO> photoLinks = getPhotoLinks(id);
        hashMap.put("photoLinks", photoLinks);

        loadAllComboboxes(hashMap);
        return new ModelAndView("room.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "id", required = true) final Integer id) {
        final RoomDTO dto = toDTOConverter.apply(roomService.getFullInfo(id));

        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadAllComboboxes(hashMap);
        return new ModelAndView("room.edit", hashMap);
    }

    private ListDTO<PhotoLinkDTO> getPhotoLinks(final Integer roomId) {
        final ListDTO<PhotoLinkDTO> listDTO = new ListDTO<PhotoLinkDTO>();
        final PhotoLinkFilter filter = new PhotoLinkFilter();
        filter.setRoomId(roomId);
        filter.setFetchRoom(true);
        filter.setFetchUserAccount(true);
        listDTO.setTotalCount(photoLinkService.find(filter).size());
        final List<IPhotoLink> entities = photoLinkService.find(filter);

        listDTO.setList(entities.stream().map(photoLinkToDTOConverter).collect(Collectors.toList()));
        return listDTO;
    }

    private void loadAllComboboxes(final Map<String, Object> hashMap) {
        loadComboboxesRoomTypes(hashMap);
        loadComboboxesAccomodations(hashMap);
        loadComboboxesViews(hashMap);
    }

    private void loadComboboxesRoomTypes(final Map<String, Object> hashMap) {
        final List<RoomType> roomTypesList = Arrays.asList(RoomType.values());
        final Map<String, String> roomTypesMap = roomTypesList.stream()
                .collect(Collectors.toMap(RoomType::name, RoomType::name));
        hashMap.put("roomTypeChoices", roomTypesMap);
    }

    private void loadComboboxesAccomodations(final Map<String, Object> hashMap) {
        final List<Accomodation> accomodationsList = Arrays.asList(Accomodation.values());
        final Map<String, String> accomodationsMap = accomodationsList.stream()
                .collect(Collectors.toMap(Accomodation::name, Accomodation::name));
        hashMap.put("accomodationChoices", accomodationsMap);
    }

    private void loadComboboxesViews(final Map<String, Object> hashMap) {
        final List<ViewType> viewsList = Arrays.asList(ViewType.values());
        final Map<String, String> viewsMap = viewsList.stream()
                .collect(Collectors.toMap(ViewType::name, ViewType::name));
        hashMap.put("viewChoices", viewsMap);
    }
}
