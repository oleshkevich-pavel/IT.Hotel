package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IBookingStatusDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
//import com.itacademy.jd2.po.hotel.dao.api.filter.BookingStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;
import com.itacademy.jd2.po.hotel.service.IBookingStatusService;

@Service
public class BookingStatusServiceImpl implements IBookingStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingStatusServiceImpl.class);

    @Autowired
    private IBookingStatusDao dao;

    @Override
    public IBookingStatus createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IBookingStatus entity) throws PersistenceException {
        final Date modifiedOn = new Date();
        entity.setUpdated(modifiedOn);
        try {
            if (entity.getId() == null) {
                entity.setCreated(modifiedOn);
                LOGGER.info("new saved entity: {}", entity);
                dao.insert(entity);
            } else {
                LOGGER.info("updated entity: {}", entity);
                dao.update(entity);
            }
        } catch (PersistenceException e) {
            LOGGER.warn(e.getMessage());
            throw e;
        }
    }

    @Override
    public IBookingStatus get(final Integer id) {
        final IBookingStatus entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete booking_status entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all booking_status entities");
        dao.deleteAll();
    }

    @Override
    public List<IBookingStatus> getAll() {
        final List<IBookingStatus> all = dao.selectAll();
        LOGGER.debug("total count booking_status entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IBookingStatus> find(final BookingStatusFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final BookingStatusFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IBookingStatus getFullInfo(final Integer id) {
        final IBookingStatus entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IBookingStatus> getAllFullInfo() {
        final List<IBookingStatus> all = dao.getAllFullInfo();
        LOGGER.debug("total count booking_status entities in DB: {}", all.size());
        return all;
    }
}
