package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IGuestStatus extends IBaseEntity {

    String getName();

    void setName(String name);

    Integer getDiscount();

    void setDiscount(Integer discount);
}
