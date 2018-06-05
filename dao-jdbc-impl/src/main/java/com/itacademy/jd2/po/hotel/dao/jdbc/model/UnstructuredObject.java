package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import java.util.Arrays;

import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class UnstructuredObject extends BaseEntity implements IUnstructuredObject {

    private String name;

    private byte[] content;

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
