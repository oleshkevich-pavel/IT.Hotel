package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.UnstructuredObjectFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;

public interface IUnstructuredObjectService {

    IUnstructuredObject get(Integer id);

    @Transactional
    void save(IUnstructuredObject entity);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IUnstructuredObject createEntity();

    List<IUnstructuredObject> getAll();

    List<IUnstructuredObject> find(UnstructuredObjectFilter filter);

    long getCount(UnstructuredObjectFilter filter);

    IUnstructuredObject getFullInfo(Integer id);

    List<IUnstructuredObject> getAllFullInfo();
}
