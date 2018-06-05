package com.itacademy.jd2.po.hotel.dao.api.filter;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;

public class RoomFilter extends AbstractFilter {

    private Integer roomNumber;

    private Date checkIn;

    private Date checkOut;

    private Accomodation accomodation;

    private ViewType view;

    private Double priceMin;

    private Double priceMax;

    private Boolean dirty;

    private Boolean broken;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(final Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(final Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(final Date checkOut) {
        this.checkOut = checkOut;
    }

    public Accomodation getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(final Accomodation accomodation) {
        this.accomodation = accomodation;
    }

    public ViewType getView() {
        return view;
    }

    public void setView(final ViewType view) {
        this.view = view;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(final Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(final Double priceMax) {
        this.priceMax = priceMax;
    }

    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(final Boolean dirty) {
        this.dirty = dirty;
    }

    public Boolean getBroken() {
        return broken;
    }

    public void setBroken(final Boolean broken) {
        this.broken = broken;
    }
}
