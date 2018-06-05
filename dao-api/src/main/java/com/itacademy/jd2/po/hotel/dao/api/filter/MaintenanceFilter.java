package com.itacademy.jd2.po.hotel.dao.api.filter;

public class MaintenanceFilter extends AbstractFilter {

    private String name;

    private Boolean available;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(final Boolean available) {
        this.available = available;
    }
}
