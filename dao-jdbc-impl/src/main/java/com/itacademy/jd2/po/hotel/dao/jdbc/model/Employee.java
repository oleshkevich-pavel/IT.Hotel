package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class Employee extends BaseEntity implements IEmployee {

    private IUserAccount userAccount;
    private Date hiring;
    private Date layoff;
    private IPost post;

    @Override
    public IUserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public void setUserAccount(final IUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public Date getHiring() {
        return hiring;
    }

    @Override
    public void setHiring(final Date hiring) {
        this.hiring = hiring;
    }

    @Override
    public Date getLayoff() {
        return layoff;
    }

    @Override
    public void setLayoff(final Date layoff) {
        this.layoff = layoff;
    }

    @Override
    public IPost getPost() {
        return post;
    }

    @Override
    public void setPost(final IPost post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Employee [ hiring=" + hiring + ", layoff=" + layoff + ", getId()=" + getId() + "]";
    }
}
