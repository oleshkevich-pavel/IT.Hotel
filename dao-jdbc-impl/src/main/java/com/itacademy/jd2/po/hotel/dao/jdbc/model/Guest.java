package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class Guest extends BaseEntity implements IGuest {

    private String verifyKey;
    private boolean verified;
    private IUserAccount userAccount;
    private IGuestStatus guestStatus;
    private Double credit;

    @Override
    public String getVerifyKey() {
        return verifyKey;
    }

    @Override
    public void setVerifyKey(final String verifyKey) {
        this.verifyKey = verifyKey;
    }

    @Override
    public boolean getVerified() {
        return verified;
    }

    @Override
    public void setVerified(final boolean verified) {
        this.verified = verified;
    }

    @Override
    public IUserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public void setUserAccount(final IUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public IGuestStatus getGuestStatus() {
        return guestStatus;
    }

    @Override
    public void setGuestStatus(final IGuestStatus guestStatus) {
        this.guestStatus = guestStatus;
    }

    @Override
    public Double getCredit() {
        return credit;
    }

    @Override
    public void setCredit(final Double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Guest [credit=" + credit + ", getId()=" + getId() + "]";
    }
}
