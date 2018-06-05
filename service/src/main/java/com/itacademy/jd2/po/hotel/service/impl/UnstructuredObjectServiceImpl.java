package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IUnstructuredObjectDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.UnstructuredObjectFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;
import com.itacademy.jd2.po.hotel.service.IUnstructuredObjectService;

@Service
public class UnstructuredObjectServiceImpl implements IUnstructuredObjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnstructuredObjectServiceImpl.class);

    @Autowired
    private IUnstructuredObjectDao dao;

    @Override
    public IUnstructuredObject createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IUnstructuredObject entity) {
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
    public IUnstructuredObject get(final Integer id) {
        IUnstructuredObject entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete unstructuredObject entity: {}", id);
        dao.delete(id);

    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all unstructuredObject entities");
        dao.deleteAll();
    }

    @Override
    public List<IUnstructuredObject> getAll() {
        List<IUnstructuredObject> all = dao.selectAll();
        LOGGER.debug("total count unstructuredObject entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IUnstructuredObject> find(final UnstructuredObjectFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final UnstructuredObjectFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IUnstructuredObject getFullInfo(final Integer id) {
        final IUnstructuredObject entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IUnstructuredObject> getAllFullInfo() {
        final List<IUnstructuredObject> all = dao.getAllFullInfo();
        LOGGER.debug("total count unstructuredObject entities in DB: {}", all.size());
        return all;
    }
}
