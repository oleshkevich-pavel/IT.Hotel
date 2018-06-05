package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;

public interface IBookingStatusDao extends IBaseDao<IBookingStatus, Integer> {

    List<IBookingStatus> find(BookingStatusFilter filter);

    long getCount(BookingStatusFilter filter);
}
