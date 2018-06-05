package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IBookingService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.service.IBookingStatusService;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;
import com.itacademy.jd2.po.hotel.web.dto.BookingDTO;

@Component
public class BookingFromDTOConverter implements Function<BookingDTO, IBooking> {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IBookingStatusService bookingStatusService;

    @Override
    public IBooking apply(final BookingDTO dto) {
        final IBooking entity = bookingService.createEntity();
        entity.setId(dto.getId());

        final IRoom room = roomService.createEntity();
        room.setId(dto.getRoomId());
        entity.setRoom(room);

        final IUserAccount userAccount = userAccountService.createEntity();
        userAccount.setId(dto.getUserAccountId());
        entity.setUserAccount(userAccount);

        final IBookingStatus bookingStatus = bookingStatusService.createEntity();
        bookingStatus.setId(dto.getBookingStatusId());
        entity.setBookingStatus(bookingStatus);

        entity.setCheckIn(dto.getCheckIn());
        entity.setCheckOut(dto.getCheckOut());
        entity.setRoomPrice(dto.getRoomPrice());
        entity.setVersion(dto.getVersion());

        return entity;
    }
}
