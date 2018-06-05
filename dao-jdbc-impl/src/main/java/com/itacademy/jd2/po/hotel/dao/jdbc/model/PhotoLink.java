package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;

public class PhotoLink extends BaseEntity implements IPhotoLink {

    private IUserAccount userAccount;
    private IRoom room;
    private String link;

    @Override
    public IRoom getRoom() {
        return room;
    }

    @Override
    public void setRoom(final IRoom room) {
        this.room = room;
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
    public String getLink() {
        return link;
    }

    @Override
    public void setLink(final String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "PhotoLink [ link=" + link + ", getId()=" + getId() + "]";
    }

}
