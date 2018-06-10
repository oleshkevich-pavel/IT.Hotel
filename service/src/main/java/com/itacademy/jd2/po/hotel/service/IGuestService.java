package com.itacademy.jd2.po.hotel.service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import com.itacademy.jd2.po.hotel.dao.api.filter.GuestFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public interface IGuestService {

    IGuest createEntity();

    IUserAccount createUserAccountEntity();

    @Transactional
    void save(IUserAccount userAccount, IGuest entity) throws PersistenceException;

    IGuest get(Integer id);

    @Transactional
    void delete(Integer id);

    @Transactional
    void deleteAll();

    List<IUserAccount> getAllUserAccounts();

    List<IGuest> getAll();

    List<IGuest> find(GuestFilter filter);

    long getCount(GuestFilter filter);

    IGuest getFullInfo(Integer id);

    List<IGuest> getAllFullInfo();

    Integer getDiscount(Integer id);
}
