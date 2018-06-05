package com.itacademy.jd2.po.hotel.dao.api.filter;

public class MessageFilter extends AbstractFilter {

    private String name;

    private String phone;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

}
