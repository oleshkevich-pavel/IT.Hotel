package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.GuestFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;

public interface IGuestDao extends IBaseDao<IGuest, Integer> {

    List<IGuest> find(GuestFilter filter);

    long getCount(GuestFilter filter);
}
