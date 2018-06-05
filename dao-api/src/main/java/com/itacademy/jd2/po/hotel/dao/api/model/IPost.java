package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IPost extends IBaseEntity {

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    IPost getSupervisor();

    void setSupervisor(IPost supervisor);
}
