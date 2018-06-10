package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.transaction.Transactional;

public interface IAbstractService<T, DAO, ID, FILTER> {

    T createEntity();

    T get(ID id);

    @Transactional
    void save(T entity);

    @Transactional
    void delete(ID id);

    @Transactional
    void deleteAll();

    List<T> getAll();

    long getCount(FILTER filter);

    T getFullInfo(ID id);

    List<T> getAllFullInfo();

    List<T> find(FILTER filter);
}
