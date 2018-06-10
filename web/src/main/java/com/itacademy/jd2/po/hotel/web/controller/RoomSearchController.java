package com.itacademy.jd2.po.hotel.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IHibernateSearchService;
import com.itacademy.jd2.po.hotel.service.IPhotoLinkService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.web.converter.PhotoLinkToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.RoomToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.BookingDTO;
import com.itacademy.jd2.po.hotel.web.dto.PhotoLinkDTO;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.Slider;
import com.itacademy.jd2.po.hotel.web.dto.search.RoomSearchDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/roomsearch")
public class RoomSearchController extends AbstractController<RoomDTO, RoomFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = RoomSearchController.class.getSimpleName() + "_SEACH_DTO";

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IPhotoLinkService photoLinkService;
    @Autowired
    private IGuestService guestService;
    @Autowired
    private RoomToDTOConverter roomToDTOConverter;
    @Autowired
    private PhotoLinkToDTOConverter photoLinkToDTOConverter;
    @Autowired
    private IHibernateSearchService hibernateSearchService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @Valid @ModelAttribute(SEARCH_FORM_MODEL) RoomSearchDTO searchDTO,
            @ModelAttribute("formModel") BookingDTO bookingDTO,
            @RequestParam(name = "page", required = false) final Integer pageNumber) {

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDTO = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }

        final ListDTO<RoomDTO> listRoomDTO = getListDTO(req);
        listRoomDTO.setPage(pageNumber);
        listRoomDTO.setSort("number:asc");

        List<IRoom> entities = getRequiredRooms(searchDTO, listRoomDTO);
        listRoomDTO.setList(entities.stream().map(roomToDTOConverter).collect(Collectors.toList()));

        final Map<RoomDTO, ListDTO<PhotoLinkDTO>> roomsWithPhotoLinks = getRoomsWithFotoLinks(listRoomDTO);

        final HashMap<String, Object> models = new HashMap<>();
        // кладем listRoomDTO для пагинации
        models.put(ListDTO.SESSION_ATTR_NAME, listRoomDTO);
        models.put("roomsWithPhotoLinks", roomsWithPhotoLinks);
        models.put(SEARCH_FORM_MODEL, searchDTO);

        models.put(Slider.SESSION_SLIDER_NAME, getSlider(searchDTO));
        return new ModelAndView("roomsearch.list", models);
    }

    private Map<RoomDTO, ListDTO<PhotoLinkDTO>> getRoomsWithFotoLinks(final ListDTO<RoomDTO> listRoomDTO) {
        final Map<RoomDTO, ListDTO<PhotoLinkDTO>> roomsWithPhotoLinks = new TreeMap<RoomDTO, ListDTO<PhotoLinkDTO>>();
        double discount = 0.00;
        if (!AuthHelper.isUserAnonymous()) {
            discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
        }
        for (int i = 0; i < listRoomDTO.getList().size(); i++) {
            final RoomDTO room = listRoomDTO.getList().get(i);
            Double roomPrice = room.getActualPrice();
            room.setActualPrice(roomPrice * (1 - discount / 100));
            final Integer roomNumber = room.getNumber();
            final ListDTO<PhotoLinkDTO> listPhotoLinkDTO = getFiveFirstPhotoLinks(roomNumber);
            roomsWithPhotoLinks.put(room, listPhotoLinkDTO);
        }
        return roomsWithPhotoLinks;
    }

    private List<IRoom> getRequiredRooms(final RoomSearchDTO searchDTO, final ListDTO<RoomDTO> listRoomDTO) {
        List<IRoom> entities = new ArrayList<IRoom>();
        List<IRoom> allEntities = null;
        String description = searchDTO.getDescription();
        String comment = searchDTO.getComment();
        if (StringUtils.isNotBlank(description)) {
            allEntities = hibernateSearchService.fuzzySearch(IRoom.class, "description", description);
        }
        if (StringUtils.isNotBlank(comment)) {
            List<IComment> allComments = hibernateSearchService.fuzzySearch(IComment.class, "comment", comment);
            allEntities = allComments.stream().map(c -> c.getRoom()).collect(Collectors.toList());
            /*
             * for (IComment iComment : allComments) { allEntities = new
             * ArrayList<IRoom>(); allEntities.add(iComment.getRoom());// здесь
             * нужен был eager }
             */
        }
        if (allEntities != null) {
            listRoomDTO.setTotalCount(allEntities.size());
            int page = listRoomDTO.getPage();
            int itemsPerPage = listRoomDTO.getItemsPerPage();
            // paging на случай hibernate search
            for (int i = (page - 1) * itemsPerPage; i < Math.min(allEntities.size(), page * itemsPerPage); i++) {
                entities.add(allEntities.get(i));
            }
        } else {
            final RoomFilter filter = applyRoomFilter(searchDTO);
            if ((filter.getCheckIn() == null) || (filter.getCheckOut() == null)) {
                listRoomDTO.setTotalCount(roomService.getCount(filter));
                applySortAndOrder2Filter(listRoomDTO, filter);
                entities = roomService.find(filter);
            } else {
                listRoomDTO.setTotalCount(roomService.findFreeRooms(filter).size());
                applySortAndOrder2Filter(listRoomDTO, filter);
                entities = roomService.findFreeRooms(filter);
            }
        }
        return entities;
    }

    private RoomFilter applyRoomFilter(final RoomSearchDTO searchDTO) {
        final RoomFilter filter = new RoomFilter();
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
        Double priceMin = searchDTO.getPriceMin();
        if (priceMin != null) {
            priceMin = getFilterPrice(priceMin);
            filter.setPriceMin(priceMin);
        }
        Double priceMax = searchDTO.getPriceMax();
        if (priceMax != null) {
            priceMax = getFilterPrice(priceMax);
            filter.setPriceMax(priceMax);
        }
        return filter;
    }

    private Double getFilterPrice(Double price) {
        if (!AuthHelper.isUserAnonymous()) {
            double discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
            if (discount == 100) {
                price = 0.00;
            } else {
                price = price * 100 / (100 - discount);
            }
        }
        return price;
    }

    private ListDTO<PhotoLinkDTO> getFiveFirstPhotoLinks(final Integer roomNumber) {
        final ListDTO<PhotoLinkDTO> listDTO = new ListDTO<PhotoLinkDTO>();
        final PhotoLinkFilter filter = new PhotoLinkFilter();
        filter.setRoomNumber(roomNumber);
        filter.setFetchRoom(true);
        filter.setFetchUserAccount(true);
        filter.setLimit(5);
        // возможно добавить в фильтр - автор только кто-то из сотрудников

        // listDTO.setTotalCount(photoLinkService.find(filter).size());
        final List<IPhotoLink> entities = photoLinkService.find(filter);

        listDTO.setList(entities.stream().map(photoLinkToDTOConverter).collect(Collectors.toList()));
        listDTO.setTotalCount(photoLinkService.find(filter).size());
        return listDTO;
    }

    private Slider getSlider(final RoomSearchDTO searchDTO) {
        Double max = roomService.getMaxPrice();

        if (!AuthHelper.isUserAnonymous()) {
            double discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
            max = Math.ceil(max * (1 - discount / 100));
        }

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

    private RoomSearchDTO getSearchDTO(final HttpServletRequest req) {
        RoomSearchDTO searchDTO = (RoomSearchDTO) req.getSession().getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new RoomSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }
}
