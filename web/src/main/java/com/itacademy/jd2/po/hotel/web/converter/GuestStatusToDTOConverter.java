package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.web.dto.GuestStatusDTO;

@Component
public class GuestStatusToDTOConverter implements Function<IGuestStatus, GuestStatusDTO> {

    @Override
    public GuestStatusDTO apply(final IGuestStatus entity) {
        final GuestStatusDTO guestStatusDTO = new GuestStatusDTO();
        guestStatusDTO.setId(entity.getId());
        guestStatusDTO.setName(entity.getName());
        guestStatusDTO.setDiscount(entity.getDiscount());
        guestStatusDTO.setCreated(entity.getCreated());
        guestStatusDTO.setUpdated(entity.getUpdated());
        return guestStatusDTO;
    }

}
