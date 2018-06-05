package com.itacademy.jd2.po.hotel.web.dto.search;

public class MaintenanceSearchDTO {

    private String name;

    private boolean available;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
    }
}
