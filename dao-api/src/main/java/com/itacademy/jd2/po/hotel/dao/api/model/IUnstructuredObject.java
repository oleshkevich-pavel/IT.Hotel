package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IUnstructuredObject extends IBaseEntity {

    String getName();

    void setName(String name);

    byte[] getContent();

    void setContent(byte[] content);

    IUserAccount getUserAccount();

    void setUserAccount(IUserAccount userAccount);
}
