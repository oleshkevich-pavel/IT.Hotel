package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.GuestStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;

public interface IGuestStatusService {

    IGuestStatus get(Integer id);

    List<IGuestStatus> getAll();

    @Transactional
    void save(IGuestStatus entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IGuestStatus createEntity();

    List<IGuestStatus> find(GuestStatusFilter filter);

    long getCount(GuestStatusFilter filter);

    IGuestStatus getFullInfo(Integer id);

    List<IGuestStatus> getAllFullInfo();

    Integer getMinDiscount();
}
