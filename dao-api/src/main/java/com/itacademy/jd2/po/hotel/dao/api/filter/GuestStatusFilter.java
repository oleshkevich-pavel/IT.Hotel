package com.itacademy.jd2.po.hotel.dao.api.filter;

public class GuestStatusFilter extends AbstractFilter {

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
