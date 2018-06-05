package com.itacademy.jd2.po.hotel.web.converter;

import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.web.dto.BookingDTO;

@Component
public class BookingToDTOConverter implements Function<IBooking, BookingDTO> {

    @Override
    public BookingDTO apply(final IBooking entity) {
        final BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(entity.getId());
        IRoom room = entity.getRoom();
        if (room != null) {
            bookingDTO.setRoomId(room.getId());
            bookingDTO.setRoomNumber(room.getNumber());
        }

        IUserAccount userAccount = entity.getUserAccount();
        if (userAccount != null) {
            bookingDTO.setUserAccountId(userAccount.getId());
            bookingDTO.setUserAccountEmail(userAccount.getEmail());
        }

        final IBookingStatus bookingStatus = entity.getBookingStatus();
        if (bookingStatus != null) {
            bookingDTO.setBookingStatusId(bookingStatus.getId());
            bookingDTO.setBookingStatusName(bookingStatus.getName());
            bookingDTO.setBookingStatusColor(bookingStatus.getColor());
        }

        bookingDTO.setCheckIn(entity.getCheckIn());
        bookingDTO.setCheckOut(entity.getCheckOut());
        if (bookingDTO.getCheckIn() != null && bookingDTO.getCheckOut() != null) {
            bookingDTO.setPeriod(daysBetween(bookingDTO.getCheckIn(), bookingDTO.getCheckOut()) + 1);
        }
        bookingDTO.setColspan(bookingDTO.getPeriod());

        bookingDTO.setRoomPrice(entity.getRoomPrice());
        bookingDTO.setVersion(entity.getVersion());
        bookingDTO.setCreated(entity.getCreated());
        bookingDTO.setUpdated(entity.getUpdated());

        return bookingDTO;
    }

    public int daysBetween(final Date d1, final Date d2) {
        return (int) (Math.abs((d2.getTime() - d1.getTime())) / (1000 * 60 * 60 * 24));
    }
}
