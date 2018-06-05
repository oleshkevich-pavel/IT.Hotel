package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import javax.persistence.PersistenceException;

public interface IBaseDao<ENTITY, ID> {

    ENTITY createEntity();

    ENTITY get(ID id);

    void update(ENTITY entity) throws PersistenceException;

    void insert(ENTITY entity) throws PersistenceException;

    void delete(ID id);

    void deleteAll();

    List<ENTITY> selectAll();

    ENTITY getFullInfo(ID id);

    List<ENTITY> getAllFullInfo();
}
