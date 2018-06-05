package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IMessage extends IBaseEntity {

    String getName();

    void setName(String name);

    String getPhone();

    void setPhone(String phone);

    String getEmail();

    void setEmail(String email);

    String getMessage();

    void setMessage(String message);
}
