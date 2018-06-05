package com.itacademy.jd2.po.hotel.dao.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;

@Entity
public class Message extends BaseEntity implements IMessage {

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String message;

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message [name=" + name + ", phone=" + phone + ", email=" + email + ", message=" + message + ", getId()="
                + getId() + "]";
    }
}
