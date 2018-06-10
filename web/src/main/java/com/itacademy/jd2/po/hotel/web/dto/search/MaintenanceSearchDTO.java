package com.itacademy.jd2.po.hotel.web.dto.search;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

public class MaintenanceSearchDTO {

    private Integer id;

    private String name;

    @Min(0)
    @Digits(integer = 10, fraction = 2)
    private Double priceMin;

    @Digits(integer = 10, fraction = 2)
    private Double priceMax;

    private boolean available;

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
    }
}
