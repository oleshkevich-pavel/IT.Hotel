package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IPhotoLinkService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.dto.PhotoLinkDTO;

@Component
public class PhotoLinkFromDTOConverter implements Function<PhotoLinkDTO, IPhotoLink> {

    @Autowired
    private IPhotoLinkService photoLinkService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserAccountService userAccountService;

    @Override
    public IPhotoLink apply(final PhotoLinkDTO dto) {
        final IPhotoLink entity = photoLinkService.createEntity();
        entity.setId(dto.getId());

        final IRoom room = roomService.createEntity();
        room.setId(dto.getRoomId());
        entity.setRoom(room);

        final IUserAccount userAccount = userAccountService.createEntity();
        userAccount.setId(dto.getUserAccountId());
        entity.setUserAccount(userAccount);

        entity.setLink(dto.getLink());

        return entity;
    }
}
