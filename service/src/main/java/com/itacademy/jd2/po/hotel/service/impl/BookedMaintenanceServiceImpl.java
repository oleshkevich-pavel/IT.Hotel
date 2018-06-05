package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IBookedMaintenanceDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.BookedMaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.service.IBookedMaintenanceService;

@Service
public class BookedMaintenanceServiceImpl implements IBookedMaintenanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookedMaintenanceServiceImpl.class);

    @Autowired
    private IBookedMaintenanceDao dao;

    @Override
    public IBookedMaintenance createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IBookedMaintenance entity) {
        final Date modifiedOn = new Date();
        entity.setUpdated(modifiedOn);
        if (entity.getId() == null) {
            entity.setCreated(modifiedOn);
            LOGGER.info("new saved entity: {}", entity);
            dao.insert(entity);
        } else {
            LOGGER.info("updated entity: {}", entity);
            dao.update(entity);
        }
    }

    @Override
    public IBookedMaintenance get(final Integer id) {
        final IBookedMaintenance entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete booked_maintenance entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all booked_maintenance entities");
        dao.deleteAll();
    }

    @Override
    public List<IBookedMaintenance> getAll() {
        final List<IBookedMaintenance> all = dao.selectAll();
        LOGGER.debug("total count booked_maintenance entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IBookedMaintenance> find(final BookedMaintenanceFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final BookedMaintenanceFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IBookedMaintenance getFullInfo(final Integer id) {
        final IBookedMaintenance entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IBookedMaintenance> getAllFullInfo() {
        final List<IBookedMaintenance> all = dao.getAllFullInfo();
        LOGGER.debug("total count booked_maintenance entities in DB: {}", all.size());
        return all;
    }

    @Override
    public Double getMaxPrice() {
        return dao.getMaxPrice();
    }
}
