package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IMaintenanceDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.MaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.service.IMaintenanceService;

@Service
public class MaintenanceServiceImpl implements IMaintenanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceServiceImpl.class);

    @Autowired
    private IMaintenanceDao dao;

    @Override
    public IMaintenance createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IMaintenance entity) {
        Date modifiedOn = new Date();
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
    public IMaintenance get(final Integer id) {
        IMaintenance entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete maintenance entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all maintenance entities");
        dao.deleteAll();
    }

    @Override
    public List<IMaintenance> getAll() {
        List<IMaintenance> all = dao.selectAll();
        LOGGER.debug("total count maintenance entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IMaintenance> find(final MaintenanceFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final MaintenanceFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IMaintenance getFullInfo(final Integer id) {
        final IMaintenance entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IMaintenance> getAllFullInfo() {
        final List<IMaintenance> all = dao.getAllFullInfo();
        LOGGER.debug("total count maintenance entities in DB: {}", all.size());
        return all;
    }

    @Override
    public Double getMaxPrice() {
        return dao.getMaxPrice();
    }
}
