package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.service.IMessageService;
import com.itacademy.jd2.po.hotel.web.dto.MessageDTO;

@Component
public class MessageFromDTOConverter implements Function<MessageDTO, IMessage> {

    @Autowired
    private IMessageService userAccountService;

    @Override
    public IMessage apply(final MessageDTO dto) {
        final IMessage entity = userAccountService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setMessage(dto.getMessage());
        return entity;
    }
}
