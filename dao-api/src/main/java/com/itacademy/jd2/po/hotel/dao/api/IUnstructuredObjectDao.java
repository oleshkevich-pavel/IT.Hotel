package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.UnstructuredObjectFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;

public interface IUnstructuredObjectDao extends IBaseDao<IUnstructuredObject, Integer> {

    List<IUnstructuredObject> find(UnstructuredObjectFilter filter);

    long getCount(UnstructuredObjectFilter filter);
}
