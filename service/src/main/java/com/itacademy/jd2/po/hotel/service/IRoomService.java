package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;

public interface IRoomService {

    IRoom get(Integer id);

    List<IRoom> getAll();

    @Transactional
    void save(IRoom entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IRoom createEntity();

    List<IRoom> find(RoomFilter filter);

    long getCount(RoomFilter filter);

    IRoom getFullInfo(Integer id);

    List<IRoom> getAllFullInfo();

    List<IRoom> findFreeRooms(RoomFilter filter);

    Double getMaxPrice();
}
