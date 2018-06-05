package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoomDTO implements Comparable<RoomDTO> {

    private Integer id;

    @Min(0) // should be the same as in DB constraints
    @NotNull
    private Integer number;

    @Min(0)
    @NotNull
    private Integer floor;

    @Size(min = 1, max = 50)
    private String type;

    @Size(min = 1, max = 50)
    private String accomodation;

    @Size(min = 1, max = 50)
    private String view;

    @Min(0)
    @Digits(integer = 10, fraction = 2) // валидация на 2 знака после запятой
    @NotNull
    private Double actualPrice;

    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    private Boolean dirty;

    @NotNull
    private Boolean broken;

    private Date created;
    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(final Integer floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(final String accomodation) {
        this.accomodation = accomodation;
    }

    public String getView() {
        return view;
    }

    public void setView(final String view) {
        this.view = view;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(final Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

    @Override
    public int compareTo(final RoomDTO room) {
        return (this.number - room.number);
    }
}
