package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

public class MaintenanceDTO {

    private Integer id;

    @Size(min = 1, max = 50) // should be the same as in DB constraints
    private String name;

    @Min(0)
    @Digits(integer = 10, fraction = 2) // валидация на 2 знака после запятой
    @NotNull
    private Double actualPrice;

    @URL
    @Size(max = 300)
    private String photoLink;

    @NotNull
    private boolean available;

    private Date created;
    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(final Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(final String photoLink) {
        this.photoLink = photoLink;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
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
}
