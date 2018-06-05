package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.ICommentDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.CommentFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private ICommentDao dao;

    @Override
    public IComment createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IComment entity) {
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
    public IComment get(final Integer id) {
        IComment entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete comment entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all comment entities");
        dao.deleteAll();
    }

    @Override
    public List<IComment> getAll() {
        List<IComment> all = dao.selectAll();
        LOGGER.debug("total count comment entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IComment> find(final CommentFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final CommentFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IComment getFullInfo(final Integer id) {
        final IComment entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IComment> getAllFullInfo() {
        final List<IComment> all = dao.getAllFullInfo();
        LOGGER.debug("total count comment entities in DB: {}", all.size());
        return all;
    }
}
