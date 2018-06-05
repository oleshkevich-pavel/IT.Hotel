package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class Booking extends BaseEntity implements IBooking {

    private IRoom room;

    private IUserAccount userAccount;

    private Date checkIn;

    private Date checkOut;

    private Double roomPrice;

    private IBookingStatus bookingStatus;

    private Integer version;

    @Override
    public Date getCheckIn() {
        return checkIn;
    }

    @Override
    public void setCheckIn(final Date checkIn) {
        this.checkIn = checkIn;
    }

    @Override
    public Date getCheckOut() {
        return checkOut;
    }

    @Override
    public void setCheckOut(final Date checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public void setRoomPrice(final Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    @Override
    public IRoom getRoom() {
        return room;
    }

    @Override
    public void setRoom(final IRoom room) {
        this.room = room;

    }

    @Override
    public IUserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public void setUserAccount(final IUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public IBookingStatus getBookingStatus() {
        return bookingStatus;
    }

    @Override
    public void setBookingStatus(final IBookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(final Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Booking [checkIn=" + checkIn + ", checkOut=" + checkOut + ", roomPrice=" + roomPrice + ", getId()="
                + getId() + "]";
    }
}
