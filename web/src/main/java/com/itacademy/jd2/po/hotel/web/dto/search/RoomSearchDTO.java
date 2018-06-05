package com.itacademy.jd2.po.hotel.web.dto.search;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import com.itacademy.jd2.po.hotel.dao.api.model.enums.Accomodation;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.ViewType;
import com.itacademy.jd2.po.hotel.web.validator.TodayOrFuture;

public class RoomSearchDTO {

    private Integer roomId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @TodayOrFuture
    private Date checkIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @TodayOrFuture
    private Date checkOut;

    private Accomodation accomodation;

    private ViewType view;

    private String description;

    private String comment;

    @Min(0)
    @Digits(integer = 10, fraction = 2)
    private Double priceMin;

    @Digits(integer = 10, fraction = 2)
    private Double priceMax;

    private Boolean dirty;

    private Boolean broken;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(final Integer roomId) {
        this.roomId = roomId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
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
