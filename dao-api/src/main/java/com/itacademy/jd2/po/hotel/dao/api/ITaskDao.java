package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.TaskFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.ITask;

public interface ITaskDao extends IBaseDao<ITask, Integer> {

    List<ITask> find(TaskFilter filter);

    long getCount(TaskFilter filter);
}
