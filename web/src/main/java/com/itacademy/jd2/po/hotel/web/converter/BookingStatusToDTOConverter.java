package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.web.dto.BookingStatusDTO;

@Component
public class BookingStatusToDTOConverter implements Function<IBookingStatus, BookingStatusDTO> {

    @Override
    public BookingStatusDTO apply(final IBookingStatus entity) {
        final BookingStatusDTO bookingStatusDTO = new BookingStatusDTO();
        bookingStatusDTO.setId(entity.getId());
        bookingStatusDTO.setName(entity.getName());
        bookingStatusDTO.setColor(entity.getColor());
        bookingStatusDTO.setCreated(entity.getCreated());
        bookingStatusDTO.setUpdated(entity.getUpdated());
        return bookingStatusDTO;
    }

}
