package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IMaintenance extends IBaseEntity {

    String getName();

    void setName(String name);

    Double getActualPrice();

    void setActualPrice(Double actualPrice);

    Boolean isAvailable();

    void setAvailable(Boolean available);
}
