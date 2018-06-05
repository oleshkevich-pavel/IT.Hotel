package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;

public interface IBookingStatusService {

    IBookingStatus get(Integer id);

    List<IBookingStatus> getAll();

    @Transactional
    void save(IBookingStatus entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IBookingStatus createEntity();

    List<IBookingStatus> find(BookingStatusFilter filter);

    long getCount(BookingStatusFilter filter);

    IBookingStatus getFullInfo(Integer id);

    List<IBookingStatus> getAllFullInfo();
}
