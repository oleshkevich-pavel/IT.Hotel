package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.web.dto.CommentDTO;

@Component
public class CommentToDTOConverter implements Function<IComment, CommentDTO> {

    @Override
    public CommentDTO apply(final IComment entity) {
        final CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(entity.getId());

        IRoom room = entity.getRoom();
        if (room != null) {
            commentDTO.setRoomId(room.getId());
            commentDTO.setRoomNumber(room.getNumber());
        }

        IUserAccount userAccount = entity.getUserAccount();
        if (userAccount != null) {
            commentDTO.setUserAccountId(userAccount.getId());
            commentDTO.setUserAccountEmail(userAccount.getEmail());
        }
        commentDTO.setComment(entity.getComment());
        commentDTO.setCreated(entity.getCreated());
        commentDTO.setUpdated(entity.getUpdated());

        return commentDTO;
    }

}
