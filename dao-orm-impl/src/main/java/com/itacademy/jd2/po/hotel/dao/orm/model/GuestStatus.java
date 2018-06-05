package com.itacademy.jd2.po.hotel.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;

@Entity
public class GuestStatus extends BaseEntity implements IGuestStatus {

    @Column
    private String name;
    
    @Column
    private Integer discount;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public Integer getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(final Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "GuestStatus [name=" + name + ", discount=" + discount + ", getId()=" + getId() + "]";
    }
}
