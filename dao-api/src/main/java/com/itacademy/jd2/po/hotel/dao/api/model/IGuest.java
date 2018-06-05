package com.itacademy.jd2.po.hotel.dao.api.model;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

public interface IGuest extends IBaseEntity {

    String getVerifyKey();

    void setVerifyKey(String verifyKey);

    boolean getVerified();

    void setVerified(boolean verified);

    IGuestStatus getGuestStatus();

    void setGuestStatus(IGuestStatus guestStatus);

    Double getCredit();

    void setCredit(Double credit);

    IUserAccount getUserAccount();

    void setUserAccount(IUserAccount userAccount);
}
