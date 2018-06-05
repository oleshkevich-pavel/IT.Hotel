package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.EmployeeFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public interface IEmployeeService {

    IEmployee createEntity();

    IUserAccount createUserAccountEntity();

    @Transactional
    void save(IUserAccount userAccount, IEmployee entity) throws PersistenceException;

    IEmployee get(Integer id);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    List<IUserAccount> getAllUserAccounts();

    List<IEmployee> getAll();

    List<IEmployee> find(EmployeeFilter filter);

    long getCount(EmployeeFilter filter);

    IEmployee getFullInfo(Integer id);

    List<IEmployee> getAllFullInfo();
}
