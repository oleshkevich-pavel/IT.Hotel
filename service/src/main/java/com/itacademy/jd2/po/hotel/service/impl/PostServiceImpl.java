package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IPostDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.PostFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.service.IPostService;

@Service
public class PostServiceImpl implements IPostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private IPostDao dao;

    @Override
    public IPost createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IPost entity) throws PersistenceException {
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
    public IPost get(final Integer id) {
        final IPost entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete post entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all post entities");
        dao.deleteAll();
    }

    @Override
    public List<IPost> getAll() {
        final List<IPost> all = dao.selectAll();
        LOGGER.debug("total count post entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IPost> find(final PostFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final PostFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IPost getFullInfo(final Integer id) {
        final IPost entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IPost> getAllFullInfo() {
        final List<IPost> all = dao.getAllFullInfo();
        LOGGER.debug("total count post entities in DB: {}", all.size());
        return all;
    }
}
