package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;

public interface IBookingDao extends IBaseDao<IBooking, Integer> {

    List<IBooking> find(BookingFilter filter);

    long getCount(BookingFilter filter);

    Double getMaxPrice();
}
