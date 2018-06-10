package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.MaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;

public interface IMaintenanceService {

    IMaintenance get(Integer id);

    List<IMaintenance> getAll();

    @Transactional
    void save(IMaintenance entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IMaintenance createEntity();

    List<IMaintenance> find(MaintenanceFilter filter);

    long getCount(MaintenanceFilter filter);

    IMaintenance getFullInfo(Integer id);

    List<IMaintenance> getAllFullInfo();

    Double getMaxPrice();
}
