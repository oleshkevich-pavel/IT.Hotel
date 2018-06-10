package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class BookedMaintenance extends BaseEntity implements IBookedMaintenance {

    private IUserAccount userAccount;
    private IMaintenance maintenance;
    private Date time;
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
