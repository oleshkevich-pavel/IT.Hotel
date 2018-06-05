package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.web.dto.PhotoLinkDTO;

@Component
public class PhotoLinkToDTOConverter implements Function<IPhotoLink, PhotoLinkDTO> {

    @Override
    public PhotoLinkDTO apply(final IPhotoLink entity) {
        final PhotoLinkDTO photoLinkDTO = new PhotoLinkDTO();
        photoLinkDTO.setId(entity.getId());

        IRoom room = entity.getRoom();
        if (room != null) {
            photoLinkDTO.setRoomId(room.getId());
            photoLinkDTO.setRoomNumber(room.getNumber());
        }

        IUserAccount userAccount = entity.getUserAccount();
        if (userAccount != null) {
            photoLinkDTO.setUserAccountId(userAccount.getId());
            photoLinkDTO.setUserAccountEmail(userAccount.getEmail());
        }
        photoLinkDTO.setLink(entity.getLink());
        photoLinkDTO.setCreated(entity.getCreated());
        photoLinkDTO.setUpdated(entity.getUpdated());

        return photoLinkDTO;
    }

}
