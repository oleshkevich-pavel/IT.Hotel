package com.itacademy.jd2.po.hotel.web.dto.search;

public class GuestStatusSearchDTO {
    private String name;

    private Integer discount;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(final Integer discount) {
        this.discount = discount;
    }
}
