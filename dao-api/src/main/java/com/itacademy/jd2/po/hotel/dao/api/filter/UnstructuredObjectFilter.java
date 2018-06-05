package com.itacademy.jd2.po.hotel.dao.api.filter;

public class UnstructuredObjectFilter extends AbstractFilter {

    private String name;

    private Integer userAccountId;

    private boolean fetchUserAccount;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(final Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public boolean getFetchUserAccount() {
        return fetchUserAccount;
    }

    public void setFetchUserAccount(final boolean fetchUserAccount) {
        this.fetchUserAccount = fetchUserAccount;
    }
}
