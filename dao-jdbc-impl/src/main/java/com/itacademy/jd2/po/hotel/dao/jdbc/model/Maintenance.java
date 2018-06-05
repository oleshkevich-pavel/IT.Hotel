package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;

public class Maintenance extends BaseEntity implements IMaintenance {

    private String name;
    private Double actualPrice;
    private Boolean available;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public Double getActualPrice() {
        return actualPrice;
    }

    @Override
    public void setActualPrice(final Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    @Override
    public Boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(final Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Maintenance [name=" + name + ", actualPrice=" + actualPrice + ", available=" + available + ", getId()="
                + getId() + "]";
    }
}
