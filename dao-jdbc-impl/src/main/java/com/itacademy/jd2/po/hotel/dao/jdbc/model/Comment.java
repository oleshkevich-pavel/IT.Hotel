package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.IComment;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;

public class Comment extends BaseEntity implements IComment {

    private IUserAccount userAccount;
    private IRoom room;
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
