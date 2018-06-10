package com.itacademy.jd2.po.hotel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.CommentFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.service.ICommentService;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IPhotoLinkService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.web.converter.CommentFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.CommentToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.PhotoLinkToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.RoomToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.CommentDTO;
import com.itacademy.jd2.po.hotel.web.dto.GuestCommentDTO;
import com.itacademy.jd2.po.hotel.web.dto.PhotoLinkDTO;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/roominformation")
public class RoomInformationController extends AbstractController<RoomDTO, RoomFilter> {

    @Autowired
    private IRoomService roomService;
    /*
     * @Autowired private IBookingService bookingService;
     */
    @Autowired
    private IPhotoLinkService photoLinkService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IGuestService guestService;
    /*
     * @Autowired private BookingToDTOConverter bookingToDTOConverter;
     */
    @Autowired
    private RoomToDTOConverter roomToDTOConverter;
    @Autowired
    private PhotoLinkToDTOConverter photoLinkToDTOConverter;
    @Autowired
    private CommentToDTOConverter commentToDTOConverter;
    @Autowired
    private CommentFromDTOConverter commentFromDTOConverter;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(@PathVariable(name = "id", required = true) final Integer id) {
        final IRoom dbModel = roomService.getFullInfo(id);

        if (!AuthHelper.isUserAnonymous() && "[ROLE_GUEST]".equals(AuthHelper.getLoggedUserRole())) {
            dbModel.setActualPrice(getPriceWithDiscount(dbModel));
        }

        final RoomDTO roomDTO = roomToDTOConverter.apply(dbModel);

        // final BookingDTO bookingDTO =
        // bookingToDTOConverter.apply(bookingService.createEntity());
        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", roomDTO);
        // hashMap.put("bookingModel", bookingDTO);

        ListDTO<PhotoLinkDTO> photoLinks = getPhotoLinks(id);
        hashMap.put("photoLinks", photoLinks);

        ListDTO<CommentDTO> comments = getComments(id);
        hashMap.put("comments", comments);

        final CommentDTO commentDTO = new CommentDTO();
        hashMap.put("commentModel", commentDTO);

        return new ModelAndView("roominformation.form", hashMap);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    // @valid стоит для валидации
    public Object save(@Valid @ModelAttribute("commentModel") final GuestCommentDTO commentModel,
            @PathVariable(name = "id", required = true) final Integer id, final BindingResult result) {
        if (result.hasErrors() || AuthHelper.isUserAnonymous()) {
            // на случай, если вдруг захочет отправить незарегистрированный
            // методом пост что-нибудь
            return "redirect:/roominformation/" + id;
        } else {
            CommentDTO commentDTO = applyCommentDTO(commentModel, id);
            final IComment entity = commentFromDTOConverter.apply(commentDTO);
            commentService.save(entity);
            return "redirect:/roominformation/" + id;
        }
    }

    private CommentDTO applyCommentDTO(final GuestCommentDTO commentModel, final Integer id) {
        CommentDTO commentDTO = new CommentDTO();
        //commentDTO.setId(commentModel.getId()); в jmeter тесте почему-то попадает id комнаты
        commentDTO.setComment(commentModel.getComment());
        commentDTO.setRoomId(id);
        commentDTO.setUserAccountId(AuthHelper.getLoggedUserId());
        return commentDTO;
    }

    private Double getPriceWithDiscount(final IRoom dbModel) {
        IGuest guest = guestService.getFullInfo(AuthHelper.getLoggedUserId());
        IGuestStatus guestStatus = guest.getGuestStatus();
        Double roomPrice = dbModel.getActualPrice() * (1 - guestStatus.getDiscount() / 100);
        return roomPrice;
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

    private ListDTO<CommentDTO> getComments(final Integer roomId) {
        final ListDTO<CommentDTO> listDTO = new ListDTO<CommentDTO>();
        final CommentFilter filter = new CommentFilter();
        filter.setRoomId(roomId);
        filter.setFetchRoom(true);
        filter.setFetchUserAccount(true);
        filter.setSortColumn("id");
        filter.setSortOrder(true);
        listDTO.setTotalCount(commentService.find(filter).size());
        final List<IComment> entities = commentService.find(filter);

        listDTO.setList(entities.stream().map(commentToDTOConverter).collect(Collectors.toList()));
        return listDTO;
    }
}
