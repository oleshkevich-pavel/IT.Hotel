package com.itacademy.jd2.po.hotel.dao.orm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

@Entity
public class Booking extends BaseEntity implements IBooking {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Room.class)
    private IRoom room;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    private IUserAccount userAccount;

    @Column
    private Date checkIn;

    @Column
    private Date checkOut;

    @Column
    private Double roomPrice;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BookingStatus.class)
    private IBookingStatus bookingStatus;

    @Column
    @Version
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
