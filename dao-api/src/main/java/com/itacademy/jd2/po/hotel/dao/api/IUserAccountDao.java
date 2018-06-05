package com.itacademy.jd2.po.hotel.dao.api;

import java.util.List;

import com.itacademy.jd2.po.hotel.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public interface IUserAccountDao extends IBaseDao<IUserAccount, Integer> {

    List<IUserAccount> find(UserAccountFilter filter);

    long getCount(UserAccountFilter filter);

    IUserAccount getByEmail(String email);
}
