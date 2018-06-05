package com.itacademy.jd2.po.hotel.dao.orm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IPost;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

@Entity
public class Employee implements IEmployee {

    @Id
    private Integer id;
    
    @OneToOne(fetch = FetchType.LAZY, optional = false, targetEntity = UserAccount.class)
    @PrimaryKeyJoinColumn
    private IUserAccount userAccount;
    
    @Column
    private Date hiring;
    
    @Column
    private Date layoff;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post.class)
    private IPost post;

    @Column(updatable = false)
    private Date created;

    @Column
    private Date updated;
    
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(final Date updated) {
        this.updated = updated;
    }
}
