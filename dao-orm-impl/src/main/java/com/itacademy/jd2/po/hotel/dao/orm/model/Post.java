package com.itacademy.jd2.po.hotel.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.po.hotel.dao.api.model.IPost;

@Entity
public class Post extends BaseEntity implements IPost {

    @Column
    private String name;
    
    @Column
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post.class)
    private IPost supervisor;
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public IPost getSupervisor() {
        return supervisor;
    }

    @Override
    public void setSupervisor(final IPost supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        return "Post [name=" + name + ", description=" + description + ", getId()=" + getId() + "]";
    }

}
