package com.itacademy.jd2.po.hotel.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

@Entity
public class PhotoLink extends BaseEntity implements IPhotoLink {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    private IUserAccount userAccount;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Room.class)
    private IRoom room;
    
    @Column
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
