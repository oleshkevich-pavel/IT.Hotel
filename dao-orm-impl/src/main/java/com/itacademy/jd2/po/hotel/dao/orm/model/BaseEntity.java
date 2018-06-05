package com.itacademy.jd2.po.hotel.dao.orm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;

@MappedSuperclass
public abstract class BaseEntity implements IBaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(updatable = false)
    private Date created;
    
    @Column
    private Date updated;

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
