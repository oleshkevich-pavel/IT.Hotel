package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.ICommentService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.dto.CommentDTO;

@Component
public class CommentFromDTOConverter implements Function<CommentDTO, IComment> {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserAccountService userAccountService;

    @Override
    public IComment apply(final CommentDTO dto) {
        final IComment entity = commentService.createEntity();
        entity.setId(dto.getId());

        final IRoom room = roomService.createEntity();
        room.setId(dto.getRoomId());
        entity.setRoom(room);

        final IUserAccount userAccount = userAccountService.createEntity();
        userAccount.setId(dto.getUserAccountId());
        entity.setUserAccount(userAccount);

        entity.setComment(dto.getComment());

        return entity;
    }
}
