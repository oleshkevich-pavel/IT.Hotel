package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.ITaskDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.TaskFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;
import com.itacademy.jd2.po.hotel.service.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private ITaskDao dao;

    @Override
    public ITask createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final ITask entity) throws PersistenceException {
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
    public ITask get(final Integer id) {
        ITask entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete task entity: {}", id);
        dao.delete(id);

    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all task entities");
        dao.deleteAll();
    }

    @Override
    public List<ITask> getAll() {
        List<ITask> all = dao.selectAll();
        LOGGER.debug("total count task entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<ITask> find(final TaskFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final TaskFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public ITask getFullInfo(final Integer id) {
        final ITask entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<ITask> getAllFullInfo() {
        final List<ITask> all = dao.getAllFullInfo();
        LOGGER.debug("total count task entities in DB: {}", all.size());
        return all;
    }
}
