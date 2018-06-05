package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookedMaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;

public interface IBookedMaintenanceDao extends IBaseDao<IBookedMaintenance, Integer> {

    List<IBookedMaintenance> find(BookedMaintenanceFilter filter);

    long getCount(BookedMaintenanceFilter filter);

    Double getMaxPrice();
}
