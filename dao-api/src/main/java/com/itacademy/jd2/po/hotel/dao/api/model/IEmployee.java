package com.itacademy.jd2.po.hotel.dao.api.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IEmployee extends IBaseEntity {

    Date getHiring();

    void setHiring(Date hiring);

    Date getLayoff();

    void setLayoff(Date layoff);

    IPost getPost();

    void setPost(IPost post);

    void setUserAccount(IUserAccount userAccount);

    IUserAccount getUserAccount();
}
