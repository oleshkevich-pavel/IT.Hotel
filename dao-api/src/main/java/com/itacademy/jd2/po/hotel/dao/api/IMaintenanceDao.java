package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.MaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;

public interface IMaintenanceDao extends IBaseDao<IMaintenance, Integer> {

    List<IMaintenance> find(MaintenanceFilter filter);

    long getCount(MaintenanceFilter filter);
}
