package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IBookingDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookingFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;
import com.itacademy.jd2.po.hotel.service.IBookingService;

@Service
public class BookingServiceImpl implements IBookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private IBookingDao dao;

    @Override
    public IBooking createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IBooking entity) throws PersistenceException {
        Date modifiedOn = new Date();
        entity.setUpdated(modifiedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifiedOn);
            LOGGER.info("new saved entity: {}", entity);
            dao.insert(entity);
        } else {
            try {
                LOGGER.info("updated entity: {}", entity);
                dao.update(entity);
            } catch (OptimisticLockException e) {
                LOGGER.warn("OptimisticLockException: "
                        + "Row was updated by another transaction (or unsaved-value mapping was incorrect)");
                throw e;
            } catch (PersistenceException e) {
                LOGGER.warn("PersistenceException: "
                        + "Row was deleted by another transaction (or unsaved-value mapping was incorrect)");
                throw e;
            }
        }
    }

    @Override
    public IBooking get(final Integer id) {
        IBooking entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete booking entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all booking entities");
        dao.deleteAll();
    }

    @Override
    public List<IBooking> getAll() {
        List<IBooking> all = dao.selectAll();
        LOGGER.debug("total count booking entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IBooking> find(final BookingFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final BookingFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IBooking getFullInfo(final Integer id) {
        final IBooking entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IBooking> getAllFullInfo() {
        final List<IBooking> all = dao.getAllFullInfo();
        LOGGER.debug("total count booking entities in DB: {}", all.size());
        return all;
    }

    @Override
    public Double getMaxPrice() {
        return dao.getMaxPrice();
    }
}
