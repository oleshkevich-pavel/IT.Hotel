package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IRoomDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.service.IRoomService;

@Service
public class RoomServiceImpl implements IRoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private IRoomDao dao;

    @Override
    public IRoom createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IRoom entity) throws PersistenceException {
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
    public IRoom get(final Integer id) {
        final IRoom entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete room entity: {}", id);
        dao.delete(id);

    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all room entities");
        dao.deleteAll();
    }

    @Override
    public List<IRoom> getAll() {
        final List<IRoom> all = dao.selectAll();
        LOGGER.debug("total count room entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IRoom> find(final RoomFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final RoomFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IRoom getFullInfo(final Integer id) {
        final IRoom entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IRoom> getAllFullInfo() {
        final List<IRoom> all = dao.getAllFullInfo();
        LOGGER.debug("total count room entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IRoom> findFreeRooms(final RoomFilter filter) {
        final List<IRoom> all = dao.findFreeRooms(filter);
        LOGGER.debug("total free room entities by dates in DB: {}", all.size());
        return all;
    }

    @Override
    public Double getMaxPrice() {
        return dao.getMaxPrice();
    }
}
