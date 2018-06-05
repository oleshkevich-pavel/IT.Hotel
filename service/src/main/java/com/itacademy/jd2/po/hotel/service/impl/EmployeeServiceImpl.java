package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IEmployeeDao;
import com.itacademy.jd2.po.hotel.dao.api.IUserAccountDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.EmployeeFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private IEmployeeDao employeeDao;
    @Autowired
    private IUserAccountDao userAccountDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public IEmployee createEntity() {
        return employeeDao.createEntity();
    }

    @Override
    public IUserAccount createUserAccountEntity() {
        return userAccountDao.createEntity();
    }

    /*
     * @Override public void save(final IEmployee entity) { final Date
     * modifiedOn = new Date(); entity.setUpdated(modifiedOn); if
     * (entity.getId() == null) { entity.setCreated(modifiedOn);
     * employeeDao.insert(entity); LOGGER.info("new saved entity: {}", entity);
     * } else { employeeDao.update(entity); LOGGER.info("updated entity: {}",
     * entity); } }
     */

    @Override
    public void save(final IUserAccount userAccount, final IEmployee entity) throws PersistenceException {
        final Date modifiedOn = new Date();
        userAccount.setUpdated(modifiedOn);
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        entity.setUpdated(modifiedOn);

        if (userAccount.getId() == null) {
            userAccount.setCreated(modifiedOn);
            LOGGER.info("new saved entity: {}", userAccount);
            userAccountDao.insert(userAccount);

            entity.setCreated(modifiedOn);
            entity.setId(userAccount.getId());
            entity.setUserAccount(userAccount);
            LOGGER.info("new saved entity: {}", entity);
            employeeDao.insert(entity);

            userAccount.setEmployee(entity);

        } else {
            LOGGER.info("updated entity: {}", userAccount);
            userAccountDao.update(userAccount);
            LOGGER.info("updated entity: {}", entity);
            employeeDao.update(entity);
        }
    }

    @Override
    public IEmployee get(final Integer id) {
        final IUserAccount entity = userAccountDao.get(id);
        final IEmployee employee = employeeDao.get(id);
        if (employee != null) {
            employee.setUserAccount(entity);
        }
        if (entity != null) {
            entity.setEmployee(employee);
        }
        LOGGER.debug("entityWithUserAccountById[{}]: {}, {}", id, entity, employee);
        return employee;
    }
    /*
     * @Override public void update(final IEmployee entity) { if (entity.getId()
     * == null) { throw new
     * IllegalArgumentException("This method can be used only for UPDATE"); }
     * final Date modifiedOn = new Date(); entity.setUpdated(modifiedOn);
     * employeeDao.update(entity); }
     */

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete employee entity: {}", id);
        employeeDao.delete(id);
        LOGGER.info("delete userAccount entity: {}", id);
        userAccountDao.delete(id);
    }

    @Override
    public void deleteAll() {
        // delete from A where not exists (select id from B where A.id=B.id)
        // DELETE FROM A WHERE id NOT IN (SELECT id FROM B)
        LOGGER.info("delete all employee entities");
        employeeDao.deleteAll();

        LOGGER.info("delete all userAccount entities");
        userAccountDao.deleteAll();
    }

    @Override
    public List<IUserAccount> getAllUserAccounts() {
        final List<IUserAccount> all = userAccountDao.selectAll();
        LOGGER.debug("total count userAccount entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IEmployee> getAll() {
        final List<IEmployee> all = employeeDao.selectAll();
        LOGGER.debug("total count employee entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IEmployee> find(final EmployeeFilter filter) {
        return employeeDao.find(filter);
    }

    @Override
    public long getCount(final EmployeeFilter filter) {
        return employeeDao.getCount(filter);
    }

    @Override
    public IEmployee getFullInfo(final Integer id) {
        final IEmployee entity = employeeDao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, entity);
        return entity;
    }

    @Override
    public List<IEmployee> getAllFullInfo() {
        final List<IEmployee> all = employeeDao.getAllFullInfo();
        LOGGER.debug("total count employee entities in DB: {}", all.size());
        return all;
    }
}
