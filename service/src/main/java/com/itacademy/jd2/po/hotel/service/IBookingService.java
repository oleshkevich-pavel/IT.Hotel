package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookingFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;

public interface IBookingService {

    IBooking get(Integer id);

    @Transactional
    void save(IBooking entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IBooking createEntity();

    List<IBooking> getAll();

    List<IBooking> find(BookingFilter filter);

    long getCount(BookingFilter filter);

    IBooking getFullInfo(Integer id);

    List<IBooking> getAllFullInfo();

    Double getMaxPrice();
}
