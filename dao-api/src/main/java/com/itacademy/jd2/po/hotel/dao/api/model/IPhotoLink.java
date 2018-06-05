package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IPhotoLink extends IBaseEntity {

    IUserAccount getUserAccount();

    void setUserAccount(IUserAccount userAccount);

    IRoom getRoom();

    void setRoom(IRoom room);

    String getLink();

    void setLink(String link);
}
