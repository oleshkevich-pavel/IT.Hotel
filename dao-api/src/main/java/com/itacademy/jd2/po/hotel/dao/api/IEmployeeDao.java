package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.EmployeeFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;

public interface IEmployeeDao extends IBaseDao<IEmployee, Integer> {

    List<IEmployee> find(EmployeeFilter filter);

    long getCount(EmployeeFilter filter);
}
