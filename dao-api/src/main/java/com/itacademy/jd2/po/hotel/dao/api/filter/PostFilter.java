package com.itacademy.jd2.po.hotel.dao.api.filter;

public class PostFilter extends AbstractFilter {
    private boolean fetchSupervisor;

    public boolean getFetchPost() {
        return fetchSupervisor;
    }

    public void setFetchPost(final boolean fetchSupervisor) {
        this.fetchSupervisor = fetchSupervisor;
    }
}
