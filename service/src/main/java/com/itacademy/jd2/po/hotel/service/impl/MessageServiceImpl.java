package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IMessageDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.MessageFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;
import com.itacademy.jd2.po.hotel.service.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    private IMessageDao dao;

    @Override
    public IMessage createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IMessage entity) {
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
    public IMessage get(final Integer id) {
        final IMessage entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete message entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all message entities");
        dao.deleteAll();
    }

    @Override
    public List<IMessage> getAll() {
        final List<IMessage> all = dao.selectAll();
        LOGGER.debug("total count message entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IMessage> find(final MessageFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final MessageFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<IMessage> getAllFullInfo() {
        final List<IMessage> all = dao.getAllFullInfo();
        LOGGER.debug("total count message entities in DB: {}", all.size());
        return all;
    }
}
