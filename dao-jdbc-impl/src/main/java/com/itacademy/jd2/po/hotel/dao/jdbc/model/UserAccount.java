package com.itacademy.jd2.po.hotel.dao.jdbc.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;

public class UserAccount extends BaseEntity implements IUserAccount {

    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String address;
    private String phone;
    private IGuest guest;
    private IEmployee employee;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(final Role role) {
        this.role = role;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Date getBirthday() {
        return birthday;
    }

    @Override
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(final String address) {
        this.address = address;
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
    public IEmployee getEmployee() {
        return employee;
    }

    @Override
    public void setEmployee(final IEmployee employee) {
        this.employee = employee;
    }

    @Override
    public IGuest getGuest() {
        return guest;
    }

    @Override
    public void setGuest(final IGuest guest) {
        this.guest = guest;
    }

    @Override
    public String toString() {
        return "UserAccount [email=" + email + ", password=" + password + ", role=" + role + ", firstName=" + firstName
                + ", lastName=" + lastName + ", birthday=" + birthday + ", address=" + address + ", phone=" + phone
                + ", getId()=" + getId() + "]";
    }

}
