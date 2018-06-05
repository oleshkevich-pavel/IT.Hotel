package com.itacademy.jd2.po.hotel.dao.api.model;

import java.util.Date;

import com.itacademy.jd2.po.hotel.dao.api.model.base.IBaseEntity;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;

public interface IUserAccount extends IBaseEntity {

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    Role getRole();

    void setRole(Role role);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    Date getBirthday();

    void setBirthday(Date date);

    String getAddress();

    void setAddress(String address);

    String getPhone();

    void setPhone(String phone);

    void setEmployee(IEmployee employee);

    IEmployee getEmployee();

    void setGuest(IGuest guest);

    IGuest getGuest();
}
