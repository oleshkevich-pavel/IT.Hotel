package com.itacademy.jd2.po.hotel.dao.api.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IBooking extends IBaseEntity {

    int DEFAULT_VERSION = 1;

    IRoom getRoom();

    void setRoom(IRoom room);

    IUserAccount getUserAccount();

    void setUserAccount(IUserAccount userAccount);

    Date getCheckIn();

    void setCheckIn(Date checkIn);

    Date getCheckOut();

    void setCheckOut(Date checkOut);

    Double getRoomPrice();

    void setRoomPrice(Double roomPrice);

    IBookingStatus getBookingStatus();

    void setBookingStatus(IBookingStatus bookingStatus);

    Integer getVersion();

    void setVersion(Integer version);
}
