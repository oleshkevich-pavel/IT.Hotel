package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.service.IGuestStatusService;
import com.itacademy.jd2.po.hotel.web.dto.GuestStatusDTO;

@Component
public class GuestStatusFromDTOConverter implements Function<GuestStatusDTO, IGuestStatus> {

    @Autowired
    private IGuestStatusService bookingStatusService;

    @Override
    public IGuestStatus apply(final GuestStatusDTO dto) {
        final IGuestStatus entity = bookingStatusService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDiscount(dto.getDiscount());
        return entity;
    }
}
