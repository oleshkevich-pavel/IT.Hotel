package com.itacademy.jd2.po.hotel.dao.api.filter;

import java.util.Date;

public class BookedMaintenanceFilter extends AbstractFilter {

    private Integer userAccountId;

    private String userAccountEmail;

    private String maintenanceName;

    private Date startTime;

    private Date endTime;

    private Double priceMin;

    private Double priceMax;

    private boolean fetchUserAccount;

    private boolean fetchMaintenance;

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(final Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserAccountEmail() {
        return userAccountEmail;
    }

    public void setUserAccountEmail(final String userAccountEmail) {
        this.userAccountEmail = userAccountEmail;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(final String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
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

    public boolean getFetchUserAccount() {
        return fetchUserAccount;
    }

    public void setFetchUserAccount(final boolean fetchUserAccount) {
        this.fetchUserAccount = fetchUserAccount;
    }

    public boolean getFetchMaintenance() {
        return fetchMaintenance;
    }

    public void setFetchMaintenance(final boolean fetchMaintenance) {
        this.fetchMaintenance = fetchMaintenance;
    }
}
