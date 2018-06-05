package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IBookingStatus extends IBaseEntity {

    String getName();

    void setName(String name);

    String getColor();

    void setColor(String color);

}
