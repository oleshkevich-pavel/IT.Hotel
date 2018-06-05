package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.itacademy.jd2.po.hotel.web.validator.TodayOrFuture;

public class GuestBookingDTO {

    @NotNull
    private Integer roomId;

    private Integer roomNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TodayOrFuture
    @NotNull
    private Date checkIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TodayOrFuture
    @NotNull
    private Date checkOut;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(final Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(final Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(final Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(final Date checkOut) {
        this.checkOut = checkOut;
    }
}
