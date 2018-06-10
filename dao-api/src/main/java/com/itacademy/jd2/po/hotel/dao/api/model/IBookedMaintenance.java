package com.itacademy.jd2.po.hotel.dao.api.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IBookedMaintenance extends IBaseEntity {

    IUserAccount getUserAccount();

    void setUserAccount(IUserAccount userAccount);

    IMaintenance getMaintenance();

    void setMaintenance(IMaintenance mantenance);

    Date getTime();

    void setTime(Date time);

    Double getPrice();

    void setPrice(Double price);
}
