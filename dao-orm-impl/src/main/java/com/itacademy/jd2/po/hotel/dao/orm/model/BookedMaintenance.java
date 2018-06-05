package com.itacademy.jd2.po.hotel.dao.orm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

@Entity
public class BookedMaintenance extends BaseEntity implements IBookedMaintenance {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
    private IUserAccount userAccount;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Room.class)
    private IRoom room;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Maintenance.class)
    private IMaintenance maintenance;
    
    @Column
    private Date time;
    
    @Column
    private Double price;

    @Override
    public Date getTime() {
        return time;
    }

    @Override
    public void setTime(final Date time) {
        this.time = time;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public void setPrice(final Double price) {
        this.price = price;
    }

    @Override
    public IMaintenance getMaintenance() {
        return maintenance;
    }

    @Override
    public void setMaintenance(final IMaintenance maintenance) {
        this.maintenance = maintenance;
    }

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
    public String toString() {
        return "BookedMaintenance [price=" + price + ", time=" + time + ", getId()=" + getId() + "]";
    }
}
