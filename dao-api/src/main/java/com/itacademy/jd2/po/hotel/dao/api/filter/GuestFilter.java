package com.itacademy.jd2.po.hotel.dao.api.filter;

public class GuestFilter extends AbstractFilter {

    private String verifyKey;

    private boolean fetchGuestStatus;

    private boolean fetchUserAccount;

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(final String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public boolean getFetchGuestStatus() {
        return fetchGuestStatus;
    }

    public void setFetchGuestStatus(final boolean fetchGuestStatus) {
        this.fetchGuestStatus = fetchGuestStatus;
    }

    public boolean getFetchUserAccount() {
        return fetchUserAccount;
    }

    public void setFetchUserAccount(final boolean fetchUserAccount) {
        this.fetchUserAccount = fetchUserAccount;
    }
}
