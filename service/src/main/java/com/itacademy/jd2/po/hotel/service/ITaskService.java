package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.TaskFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;

public interface ITaskService {

    ITask get(Integer id);

    @Transactional
    void save(ITask entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    ITask createEntity();

    List<ITask> getAll();

    List<ITask> find(TaskFilter filter);

    long getCount(TaskFilter filter);

    ITask getFullInfo(Integer id);

    List<ITask> getAllFullInfo();
}
