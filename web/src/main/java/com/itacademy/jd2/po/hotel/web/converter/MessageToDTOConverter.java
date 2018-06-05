package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.web.dto.MessageDTO;

@Component
public class MessageToDTOConverter implements Function<IMessage, MessageDTO> {

    @Override
    public MessageDTO apply(final IMessage entity) {
        final MessageDTO userAccountDTO = new MessageDTO();
        userAccountDTO.setId(entity.getId());
        userAccountDTO.setName(entity.getName());
        userAccountDTO.setPhone(entity.getPhone());
        userAccountDTO.setEmail(entity.getEmail());
        userAccountDTO.setMessage(entity.getMessage());

        userAccountDTO.setCreated(entity.getCreated());
        userAccountDTO.setUpdated(entity.getUpdated());

        return userAccountDTO;
    }

}
