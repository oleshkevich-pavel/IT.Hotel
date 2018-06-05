package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public interface IUserAccountService {

    IUserAccount createEntity();

    @Transactional
    void save(IUserAccount entity) throws PersistenceException;

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    IUserAccount get(Integer id);

    List<IUserAccount> getAll();

    List<IUserAccount> find(UserAccountFilter filter);

    long getCount(UserAccountFilter filter);

    IUserAccount getFullInfo(Integer id);

    IUserAccount getByEmail(String email);

    List<IUserAccount> getAllFullInfo();

    boolean isPasswordCorrect(IUserAccount userAccount, String password);
}
