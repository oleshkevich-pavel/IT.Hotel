package com.itacademy.jd2.po.hotel.web.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class GuestDTO {

    private Integer id;

    private String verifyKey;

    private boolean verified;

    // @NotNull будет null, если гость сам регистрируется, в конвертере делаем
    // проверку
    @Digits(integer = 10, fraction = 2) // валидация на 2 знака после запятой
    private Double credit;

    // @NotNull будет null, если гость сам регистрируется, в конвертере делаем
    // проверку
    private Integer guestStatusId;

    private String guestStatusName;

    private Date created;

    private Date updated;

    @NotNull
    @Valid
    private UserAccountDTO userAccount = new UserAccountDTO();

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(final String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(final boolean verified) {
        this.verified = verified;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(final Double credit) {
        this.credit = credit;
    }

    public Integer getGuestStatusId() {
        return guestStatusId;
    }

    public void setGuestStatusId(final Integer guestStatusId) {
        this.guestStatusId = guestStatusId;
    }

    public String getGuestStatusName() {
        return guestStatusName;
    }

    public void setGuestStatusName(final String guestStatusName) {
        this.guestStatusName = guestStatusName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

    public UserAccountDTO getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(final UserAccountDTO userAccount) {
        this.userAccount = userAccount;
    }
}
