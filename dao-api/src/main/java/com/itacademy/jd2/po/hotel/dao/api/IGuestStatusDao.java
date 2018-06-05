package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.GuestStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;

public interface IGuestStatusDao extends IBaseDao<IGuestStatus, Integer> {

    List<IGuestStatus> find(GuestStatusFilter filter);

    long getCount(GuestStatusFilter filter);

    Integer getMinDiscount();
}
