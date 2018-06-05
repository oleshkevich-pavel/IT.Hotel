package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.RoomFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;

public interface IRoomDao extends IBaseDao<IRoom, Integer> {

    List<IRoom> find(RoomFilter filter);

    long getCount(RoomFilter filter);

    List<IRoom> findFreeRooms(RoomFilter filter);

    Double getMaxPrice();
}
