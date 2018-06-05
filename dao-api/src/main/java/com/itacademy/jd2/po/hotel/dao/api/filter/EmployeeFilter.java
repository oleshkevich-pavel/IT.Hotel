package com.itacademy.jd2.po.hotel.dao.api.filter;

public class EmployeeFilter extends AbstractFilter {
    private boolean fetchPost;

    private boolean fetchUserAccount;

    public boolean getFetchPost() {
        return fetchPost;
    }

    public void setFetchPost(final boolean fetchPost) {
        this.fetchPost = fetchPost;
    }

    public boolean getFetchUserAccount() {
        return fetchUserAccount;
    }

    public void setFetchUserAccount(final boolean fetchUserAccount) {
        this.fetchUserAccount = fetchUserAccount;
    }
}
