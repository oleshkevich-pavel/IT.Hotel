package com.itacademy.jd2.po.hotel.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;

@Entity
public class BookingStatus extends BaseEntity implements IBookingStatus {

    @Column
    private String name;

    @Column
    private String color;
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(final String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "BookingStatus [name=" + name + ", color=" + color + ", getId()=" + getId() + "]";
    }
}
