package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IPhotoLinkDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.PhotoLinkFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.service.IPhotoLinkService;

@Service
public class PhotoLinkServiceImpl implements IPhotoLinkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoLinkServiceImpl.class);

    @Autowired
    private IPhotoLinkDao dao;

    @Override
    public IPhotoLink createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IPhotoLink entity) throws PersistenceException {
        Date modifiedOn = new Date();
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
    public IPhotoLink get(final Integer id) {
        IPhotoLink entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete photo_link entity: {}", id);
        dao.delete(id);

    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all photo_link entities");
        dao.deleteAll();
    }

    @Override
    public List<IPhotoLink> getAll() {
        List<IPhotoLink> all = dao.selectAll();
        LOGGER.debug("total count photo_link entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IPhotoLink> find(final PhotoLinkFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final PhotoLinkFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IPhotoLink getFullInfo(final Integer id) {
        final IPhotoLink entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IPhotoLink> getAllFullInfo() {
        final List<IPhotoLink> all = dao.getAllFullInfo();
        LOGGER.debug("total count photo_link entities in DB: {}", all.size());
        return all;
    }
}
