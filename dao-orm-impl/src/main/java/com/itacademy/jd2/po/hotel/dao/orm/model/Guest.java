package com.itacademy.jd2.po.hotel.dao.orm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

@Entity
public class Guest implements IGuest {

    @Id
    private Integer id;

    // а надо ли updatable = false?
    @Column(updatable = false)
    private String verifyKey;

    @Column
    private boolean verified;

    @OneToOne(fetch = FetchType.LAZY, optional = false, targetEntity = UserAccount.class)
    @PrimaryKeyJoinColumn
    private IUserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = GuestStatus.class)
    private IGuestStatus guestStatus;

    @Column
    private Double credit;

    @Column(updatable = false)
    private Date created;

    @Column
    private Date updated;

    @Override
    public String getVerifyKey() {
        return verifyKey;
    }

    @Override
    public void setVerifyKey(final String verifyKey) {
        this.verifyKey = verifyKey;
    }

    @Override
    public boolean getVerified() {
        return verified;
    }

    @Override
    public void setVerified(final boolean verified) {
        this.verified = verified;
    }

    @Override
    public IUserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public void setUserAccount(final IUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public IGuestStatus getGuestStatus() {
        return guestStatus;
    }

    @Override
    public void setGuestStatus(final IGuestStatus guestStatus) {
        this.guestStatus = guestStatus;
    }

    @Override
    public Double getCredit() {
        return credit;
    }

    @Override
    public void setCredit(final Double credit) {
        this.credit = credit;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public void setCreated(final Date created) {
        this.created = created;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Guest [id=" + id + ", verifyKey=" + verifyKey + ", verified=" + verified + ", credit="
                + credit + "]";
    }
}
