package com.itacademy.jd2.po.hotel.web.converter;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.service.IBookingStatusService;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IRoomService;
import com.itacademy.jd2.po.hotel.web.dto.BookingDTO;
import com.itacademy.jd2.po.hotel.web.dto.GuestBookingDTO;
import com.itacademy.jd2.po.hotel.web.security.AuthHelper;

@Component
public class BookingFromGuestBookingDTOConverter implements Function<GuestBookingDTO, IBooking> {

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IBookingStatusService bookingStatusService;
    @Autowired
    private IGuestService guestService;
    @Autowired
    private BookingFromDTOConverter bookingFromDTOConverter;

    @Override
    public IBooking apply(final GuestBookingDTO guestBookingDTO) {
        BookingDTO dto = new BookingDTO();
        dto.setCheckIn(guestBookingDTO.getCheckIn());
        dto.setCheckOut(guestBookingDTO.getCheckOut());
        dto.setRoomId(guestBookingDTO.getRoomId());
        dto.setUserAccountId(AuthHelper.getLoggedUserId());

        Integer discount = guestService.getDiscount(AuthHelper.getLoggedUserId());
        Integer roomId = guestBookingDTO.getRoomId();
        IRoom room = roomService.get(roomId);
        dto.setRoomPrice(room.getActualPrice() * (1 - discount / 100));

        final BookingStatusFilter bookingStatusFilter = new BookingStatusFilter();
        bookingStatusFilter.setName("забронирован");
        final IBookingStatus bookingStatus = bookingStatusService.find(bookingStatusFilter).get(0);
        dto.setBookingStatusId(bookingStatus.getId());

        final IBooking entity = bookingFromDTOConverter.apply(dto);
        return entity;
    }
}