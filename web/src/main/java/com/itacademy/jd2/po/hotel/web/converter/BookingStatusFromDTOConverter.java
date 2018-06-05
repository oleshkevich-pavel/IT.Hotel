package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.service.IBookingStatusService;
import com.itacademy.jd2.po.hotel.web.dto.BookingStatusDTO;

@Component
public class BookingStatusFromDTOConverter implements Function<BookingStatusDTO, IBookingStatus> {

    @Autowired
    private IBookingStatusService bookingStatusService;

    @Override
    public IBookingStatus apply(final BookingStatusDTO dto) {
        final IBookingStatus entity = bookingStatusService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setColor(dto.getColor());
        return entity;
    }
}
