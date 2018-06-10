package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.itacademy.jd2.po.hotel.web.validator.TodayOrFutureOrNull;

public class GuestBookedMaintenanceDTO {

    @NotNull
    private Integer maintenanceId;

    private String maintenanceName;

    @TodayOrFutureOrNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @DateTimeFormat(pattern = "hh:mm a")
    private Date time;

    public Integer getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(final Integer maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(final String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(final Date time) {
        this.time = time;
    }
}
