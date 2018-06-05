package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.RoomType;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;

public class Room extends BaseEntity implements IRoom {

    private Integer number;
    private Integer floor;
    private RoomType type;
    private Accomodation accomodation;
    private ViewType view;
    private Double actualPrice;
    private String description;
    private Boolean dirty;
    private Boolean broken;

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public void setNumber(final Integer number) {
        this.number = number;
    }

    @Override
    public Integer getFloor() {
        return floor;
    }

    @Override
    public void setFloor(final Integer floor) {
        this.floor = floor;
    }

    @Override
    public RoomType getType() {
        return type;
    }

    @Override
    public void setType(final RoomType type) {
        this.type = type;
    }

    @Override
    public Accomodation getAccomodation() {
        return accomodation;
    }

    @Override
    public void setAccomodation(final Accomodation accomodation) {
        this.accomodation = accomodation;
    }

    @Override
    public ViewType getView() {
        return view;
    }

    @Override
    public void setView(final ViewType view) {
        this.view = view;
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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public Boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(final Boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public Boolean isBroken() {
        return broken;
    }

    @Override
    public void setBroken(final Boolean broken) {
        this.broken = broken;
    }

    @Override
    public String toString() {
        return "Room [number=" + number + ", floor=" + floor + ", type=" + type + ", accomodation=" + accomodation
                + ", view=" + view + ", actualPrice=" + actualPrice + ", dirty=" + dirty + ", broken=" + broken
                + ", getId()=" + getId() + "]";
    }
}
