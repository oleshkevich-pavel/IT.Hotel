package com.itacademy.jd2.po.hotel.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itacademy.jd2.po.hotel.dao.api.IGuestDao;
import com.itacademy.jd2.po.hotel.dao.api.IUserAccountDao;
import com.itacademy.jd2.po.hotel.dao.api.filter.GuestFilter;
import com.itacademy.jd2.po.hotel.dao.api.filter.GuestStatusFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;
import com.itacademy.jd2.po.hotel.dao.api.model.enums.Role;
import com.itacademy.jd2.po.hotel.service.IGuestService;
import com.itacademy.jd2.po.hotel.service.IGuestStatusService;

@Service
public class GuestServiceImpl implements IGuestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestServiceImpl.class);
    @Autowired
    private IGuestDao guestDao;
    @Autowired
    private IUserAccountDao userAccountDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IGuestStatusService guestStatusService;

    @Override
    public IGuest createEntity() {
        return guestDao.createEntity();
    }

    @Override
    public IUserAccount createUserAccountEntity() {
        return userAccountDao.createEntity();
    }
    /*
     * @Override public void save(final IGuest entity) { final Date modifiedOn =
     * new Date(); entity.setUpdated(modifiedOn); if (entity.getId() == null) {
     * entity.setCreated(modifiedOn); guestDao.insert(entity);
     * LOGGER.info("new saved entity: {}", entity); } else {
     * guestDao.update(entity); LOGGER.info("updated entity: {}", entity); } }
     */

    @Override
    public void save(final IUserAccount userAccount, final IGuest entity) throws PersistenceException {
        final Date modifiedOn = new Date();
        userAccount.setUpdated(modifiedOn);
        entity.setUpdated(modifiedOn);

        if (userAccount.getId() == null) {
            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            userAccount.setCreated(modifiedOn);
            if (userAccount.getRole() == null) {
                userAccount.setRole(Role.ROLE_GUEST);
            }
            LOGGER.info("new saved entity: {}", userAccount);
            userAccountDao.insert(userAccount);
            if (entity.getGuestStatus().getName() == null) {
                // ставим статус, где скидка минимальная;
                entity.setGuestStatus(getStatusNewGuest());
            }
            entity.setCreated(modifiedOn);
            entity.setId(userAccount.getId());
            entity.setUserAccount(userAccount);
            if (entity.getCredit() == null) {
                // если гость регистрируется сам, то ставим ему credit=0;
                entity.setCredit(0.00);
            }
            LOGGER.info("new saved entity: {}", entity);
            guestDao.insert(entity);

            userAccount.setGuest(entity);
        } else {
            if (userAccount.getRole() == null) {
                userAccount.setRole(Role.ROLE_GUEST);
            }
            LOGGER.info("updated entity: {}", userAccount);
            userAccountDao.update(userAccount);

            LOGGER.info("updated entity: {}", entity);
            guestDao.update(entity);
        }
    }

    @Override
    public IGuest get(final Integer id) {
        final IUserAccount entity = userAccountDao.get(id);
        final IGuest guest = guestDao.get(id);
        if (guest != null) {
            guest.setUserAccount(entity);
        }
        if (entity != null) {
            entity.setGuest(guest);
        }
        LOGGER.debug("entityWithUserAccountById[{}]: {}, {}", id, entity, guest);
        return guest;
    }
    /*
     * @Override public void update(final IGuest entity) { if (entity.getId() ==
     * null) { throw new
     * IllegalArgumentException("This method can be used only for UPDATE"); }
     * final Date modifiedOn = new Date(); entity.setUpdated(modifiedOn);
     * guestDao.update(entity); }
     */

    @Override
    public void delete(final Integer id) {
        LOGGER.info("delete guest entity: {}", id);
        guestDao.delete(id);
        LOGGER.info("delete userAccount entity: {}", id);
        userAccountDao.delete(id);
    }

    @Override
    public void deleteAll() {
        // delete from A where not exists (select id from B where A.id=B.id)
        // DELETE FROM A WHERE id NOT IN (SELECT id FROM B)
        LOGGER.info("delete all guest entities");
        guestDao.deleteAll();
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
    public List<IGuest> getAll() {
        final List<IGuest> all = guestDao.selectAll();
        LOGGER.debug("total count guest entities in DB: {}", all.size());
        return all;
    }

    @Override
    public List<IGuest> find(final GuestFilter filter) {
        return guestDao.find(filter);
    }

    @Override
    public long getCount(final GuestFilter filter) {
        return guestDao.getCount(filter);
    }

    @Override
    public IGuest getFullInfo(final Integer id) {
        final IGuest guest = guestDao.getFullInfo(id);
        LOGGER.debug("entityById[{}]: {}", id, guest);
        return guest;
    }

    @Override
    public List<IGuest> getAllFullInfo() {
        final List<IGuest> all = guestDao.getAllFullInfo();
        LOGGER.debug("total count guest entities in DB: {}", all.size());
        return all;
    }

    @Override
    public Integer getDiscount(final Integer id) {
        Integer discount = guestDao.getFullInfo(id).getGuestStatus().getDiscount();
        LOGGER.debug("discount for current user{}: {}", id, discount);
        return discount;
    }

    private IGuestStatus getStatusNewGuest() {
        final GuestStatusFilter filter = new GuestStatusFilter();
        filter.setDiscount(guestStatusService.getMinDiscount()); // берем статус
                                                                 // гостя, где
                                                                 // минимальная
                                                                 // скидка
        final List<IGuestStatus> entities = guestStatusService.find(filter);
        return entities.get(0);
    }
}
