package com.itacademy.jd2.po.hotel.web.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.service.IPhotoLinkService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.service.IStorageService;
import com.itacademy.jd2.po.hotel.web.converter.PhotoLinkFromDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.PhotoLinkToDTOConverter;
import com.itacademy.jd2.po.hotel.web.converter.RoomToDTOConverter;
import com.itacademy.jd2.po.hotel.web.dto.PhotoLinkDTO;
import com.itacademy.jd2.po.hotel.web.dto.RoomDTO;
import com.itacademy.jd2.po.hotel.web.dto.list.ListDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/gallery")
public class GalleryController extends AbstractController<RoomDTO, RoomFilter> {

    @Autowired
    private IStorageService storageService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IPhotoLinkService photoLinkService;
    @Autowired
    private RoomToDTOConverter roomToDTOConverter;
    @Autowired
    private PhotoLinkToDTOConverter photoLinkToDTOConverter;
    @Autowired
    private PhotoLinkFromDTOConverter photoLinkFromDTOConverter;

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView index(final HttpServletRequest req,
            @RequestParam(name="file", required = false) final MultipartFile file,
            @RequestParam(name="roomid", required = false) Integer roomId
/*            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn*/) throws GeneralSecurityException, IOException {

        if (req.getMethod().equalsIgnoreCase("post")) {
            storageService.store(file);
            String url = storageService.getUploadedFileUrl(file);
            storageService.deleteAll();
            System.out.println("new uploaded file" + url);
            PhotoLinkDTO photoLinkDTO = new PhotoLinkDTO();
            photoLinkDTO.setRoomId(roomId);
            photoLinkDTO.setLink(url);
            photoLinkDTO.setUserAccountId(AuthHelper.getLoggedUserId());
            IPhotoLink entity = photoLinkFromDTOConverter.apply(photoLinkDTO);
            photoLinkService.save(entity);
        }
        final ListDTO<RoomDTO> listRoomDTO = new ListDTO<RoomDTO>()/*getListDTO(req)*/;
/*        listRoomDTO.setPage(pageNumber);
        listRoomDTO.setSort("number:asc");*/

        final List<IRoom> entities = roomService.getAllFullInfo();
        listRoomDTO.setList(entities.stream().map(roomToDTOConverter).collect(Collectors.toList()));
        listRoomDTO.setTotalCount(roomService.getAllFullInfo().size());

        final Map<RoomDTO, ListDTO<PhotoLinkDTO>> roomsWithPhotoLinks = new TreeMap<RoomDTO, ListDTO<PhotoLinkDTO>>();
        for (int i = 0; i < listRoomDTO.getList().size(); i++) {
            roomId = listRoomDTO.getList().get(i).getId();
            final ListDTO<PhotoLinkDTO> listPhotoLinkDTO = getPhotoLinks(roomId);
            roomsWithPhotoLinks.put(listRoomDTO.getList().get(i), listPhotoLinkDTO);
        }

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listRoomDTO);
        models.put("roomsWithPhotoLinks", roomsWithPhotoLinks);
        return new ModelAndView("gallery", models);
    }

/*    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam("file") final MultipartFile file,
            @RequestParam("roomid") final Integer roomId) throws GeneralSecurityException, IOException {
        storageService.store(file);
        String url = storageService.getUploadedFileUrl(file);
        storageService.deleteAll();
        System.out.println("new uploaded file" + url);
        PhotoLinkDTO photoLinkDTO = new PhotoLinkDTO();
        photoLinkDTO.setRoomId(roomId);
        photoLinkDTO.setLink(url);
        photoLinkDTO.setUserAccountId(AuthHelper.getLoggedUserId());
        IPhotoLink entity = photoLinkFromDTOConverter.apply(photoLinkDTO);
        photoLinkService.save(entity);
        final Map<String, Object> hashMap = new HashMap<>();
        // hashMap.put("message", "https://drive.google.com/open?id=" +
        // f.getId());
        // hashMap.put("message", url);
        return new ModelAndView("gallery");
    }*/

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final Map<String, Object> hashMap = new HashMap<>();
        final RoomDTO dto = new RoomDTO();
        hashMap.put("formModel", dto);
        return new ModelAndView("gallery", hashMap);
    }

    private ListDTO<PhotoLinkDTO> getPhotoLinks(final Integer roomId) {
        final ListDTO<PhotoLinkDTO> listDTO = new ListDTO<PhotoLinkDTO>();
        final PhotoLinkFilter filter = new PhotoLinkFilter();
        filter.setRoomId(roomId);
        filter.setFetchRoom(true);
        filter.setFetchUserAccount(true);
        int countPhotos = photoLinkService.find(filter).size();
        listDTO.setTotalCount(countPhotos);
        filter.setLimit(countPhotos);
        // возможно добавить в фильтр - автор только кто-то из сотрудников
        final List<IPhotoLink> entities = photoLinkService.find(filter);

        listDTO.setList(entities.stream().map(photoLinkToDTOConverter).collect(Collectors.toList()));
        return listDTO;
    }
}
