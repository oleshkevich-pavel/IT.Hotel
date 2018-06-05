package com.itacademy.jd2.po.hotel.dao.orm.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

@Entity
public class UnstructuredObject extends BaseEntity implements IUnstructuredObject {

    @Column
    private String name;

    @Column
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserAccount.class)
 //   @Cascade({CascadeType.DELETE})
    private IUserAccount userAccount;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public void setContent(final byte[] content) {
        this.content = content;
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
    public String toString() {
        return "UnstructuredObject [name=" + name + ", content=" + Arrays.toString(content) + ", getId()=" + getId()
                + "]";
    }
}
