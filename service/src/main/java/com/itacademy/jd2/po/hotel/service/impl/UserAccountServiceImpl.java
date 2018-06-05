package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IUserAccountDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IUserAccountService;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IUserAccountDao dao;

    @Override
    public IUserAccount createEntity() {
        return dao.createEntity();
    }

    @Override
    public void save(final IUserAccount entity) throws PersistenceException {
        final Date modifiedOn = new Date();
        entity.setUpdated(modifiedOn);
 //       entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        if (entity.getId() == null) {
            entity.setCreated(modifiedOn);
            LOGGER.info("new saved entity: {}", entity);
            dao.insert(entity);
        } else {
            LOGGER.info("updated entity: {}", entity);
            dao.update(entity);
        }
    }

    @Override
    public IUserAccount get(final Integer id) {
        final IUserAccount entity = dao.get(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete userAccount entity: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.info("delete all userAccount entities");
        dao.deleteAll();
    }

    @Override
    public List<IUserAccount> getAll() {
        final List<IUserAccount> all = dao.selectAll();
        LOGGER.debug("total count userAccount entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IUserAccount> find(final UserAccountFilter filter) {
        return dao.find(filter);
    }

    @Override
    public long getCount(final UserAccountFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public IUserAccount getFullInfo(final Integer id) {
        final IUserAccount entity = dao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public IUserAccount getByEmail(final String email) {
        final IUserAccount entity = dao.getByEmail(email);
        LOGGER.debug("entityByEmail[{}]: {}", email, entity);
        return entity;
    }

    @Override
    public List<IUserAccount> getAllFullInfo() {
        final List<IUserAccount> all = dao.getAllFullInfo();
        LOGGER.debug("total count userAccount entities in DB: {}", all.size());
        return all;
    }

    @Override
    public boolean isPasswordCorrect(final IUserAccount userAccount, final String password) {
        return (passwordEncoder.matches(password, userAccount.getPassword()));
    }
}
