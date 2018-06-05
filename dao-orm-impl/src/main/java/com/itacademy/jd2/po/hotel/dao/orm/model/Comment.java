package com.itacademy.jd2.po.hotel.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

@Indexed
@Entity
public class Comment extends BaseEntity implements IComment {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    private IUserAccount userAccount;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Room.class)
    private IRoom room;

    @Column
    @Field
    private String comment;

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
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(final String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment [comment=" + comment + ", getId()=" + getId() + "]";
    }

}
