package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookedMaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;

public interface IBookedMaintenanceService {
        /*extends IAbstractService<IBookedMaintenance, IBookedMaintenanceDao, Integer, BookedMaintenanceFilter> */

    IBookedMaintenance get(Integer id);

    @Transactional
    void save(IBookedMaintenance entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IBookedMaintenance createEntity();

    List<IBookedMaintenance> getAll();

    List<IBookedMaintenance> find(BookedMaintenanceFilter filter);

    long getCount(BookedMaintenanceFilter filter);

    IBookedMaintenance getFullInfo(Integer id);

    List<IBookedMaintenance> getAllFullInfo();

    Double getMaxPrice();
}
