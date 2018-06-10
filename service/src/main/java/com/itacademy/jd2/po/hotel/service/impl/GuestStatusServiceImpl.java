package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IGuestStatusDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.GuestStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.service.IGuestStatusService;

@Service
public class GuestStatusServiceImpl implements IGuestStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestStatusServiceImpl.class);

    @Autowired
    private IGuestStatusDao dao;

    @Override
    public IGuestStatus createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IGuestStatus entity) throws PersistenceException {
        Date modifiedOn = new Date();
        try {
            entity.setUpdated(modifiedOn);
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
    public IGuestStatus get(final Integer id) {
        IGuestStatus entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete guest_status entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all guest_status entities");
        dao.deleteAll();
    }

    @Override
    public List<IGuestStatus> getAll() {
        List<IGuestStatus> all = dao.selectAll();
        LOGGER.debug("total count guest_status entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IGuestStatus> find(final GuestStatusFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final GuestStatusFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IGuestStatus getFullInfo(final Integer id) {
        final IGuestStatus entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IGuestStatus> getAllFullInfo() {
        final List<IGuestStatus> all = dao.getAllFullInfo();
        LOGGER.debug("total count guest_status entities in DB: {}", all.size());
        return all;
    }

    @Override
    public Integer getMinDiscount() {
        return dao.getMinDiscount();
    }
}
