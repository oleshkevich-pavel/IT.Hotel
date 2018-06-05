package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuest;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class GuestAccountServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getGuestService().deleteAll();
        getGuestStatusService().deleteAll();
    }

    @Test
    public void testCreateWithUserAccount() {
        final IUserAccount userAccount = getNewUserAccount();
        final IGuest guest = saveNewGuest(userAccount);

        final IGuest guestFromDB = getGuestService().getFullInfo(guest.getId());

        assertEquals(guest.getId(), guestFromDB.getId());
        assertEquals(guest.getVerifyKey(), guestFromDB.getVerifyKey());
        assertEquals(guest.getVerified(), guestFromDB.getVerified());
        assertEquals(guest.getGuestStatus().getId(), guestFromDB.getGuestStatus().getId());
        assertEquals(guest.getUserAccount().getId(), guestFromDB.getUserAccount().getId());
        assertEquals(guest.getCredit(), guestFromDB.getCredit());
        assertEquals(guest.getCreated(), guestFromDB.getCreated());
        assertEquals(guest.getUpdated(), guestFromDB.getUpdated());

        assertNotNull(guestFromDB.getId());
        assertNotNull(guestFromDB.getVerifyKey());
        assertNotNull(guestFromDB.getVerified());
        assertNotNull(guestFromDB.getGuestStatus().getId());
        assertNotNull(guestFromDB.getUserAccount().getId());
        assertNotNull(guestFromDB.getCredit());
        assertNotNull(guestFromDB.getCreated());
        assertNotNull(guestFromDB.getUpdated());

        assertTrue(guestFromDB.getCreated().equals(guestFromDB.getUpdated()));

        final IUserAccount userAccountFromDB = guestFromDB.getUserAccount();
        assertEquals(userAccount.getId(), userAccountFromDB.getId());
        assertEquals(userAccount.getEmail(), userAccountFromDB.getEmail());
        assertEquals(userAccount.getPassword(), userAccountFromDB.getPassword());
        assertEquals(userAccount.getRole(), userAccountFromDB.getRole());
        assertEquals(userAccount.getFirstName(), userAccountFromDB.getFirstName());
        assertEquals(userAccount.getLastName(), userAccountFromDB.getLastName());
        assertEquals(userAccount.getBirthday(), userAccountFromDB.getBirthday());
        assertEquals(userAccount.getAddress(), userAccountFromDB.getAddress());
        assertEquals(userAccount.getPhone(), userAccountFromDB.getPhone());
        assertEquals(userAccount.getCreated(), userAccountFromDB.getCreated());
        assertEquals(userAccount.getUpdated(), userAccountFromDB.getUpdated());

        assertNotNull(userAccountFromDB.getId());
        assertNotNull(userAccountFromDB.getEmail());
        assertNotNull(userAccountFromDB.getPassword());
        assertNotNull(userAccountFromDB.getRole());
        assertNotNull(userAccountFromDB.getFirstName());
        assertNotNull(userAccountFromDB.getLastName());
        assertNotNull(userAccountFromDB.getBirthday());
        assertNotNull(userAccountFromDB.getAddress());
        assertNotNull(userAccountFromDB.getPhone());
        assertNotNull(userAccountFromDB.getCreated());
        assertNotNull(userAccountFromDB.getUpdated());

        assertTrue(userAccountFromDB.getCreated().equals(userAccountFromDB.getUpdated()));
    }

    @Test
    public void testUpdateGuestWithUserAccount() {
        final IUserAccount userAccount = getNewUserAccount();
        final IGuest guest = saveNewGuest(userAccount);

        final String newEmail = "newEmail-" + getRandomInt();
        final Double newCredit = getRandomDouble();

        final IGuest guestFromDB = getGuestService().getFullInfo(guest.getId());
        final IUserAccount userAccountFromDB = guestFromDB.getUserAccount();
        userAccountFromDB.setEmail(newEmail);
        guestFromDB.setCredit(newCredit);

        getGuestService().save(userAccountFromDB, guestFromDB);

        final IGuest updatedGuestFromDB = getGuestService().getFullInfo(guest.getId());
        assertEquals(guest.getId(), updatedGuestFromDB.getId());
        assertEquals(guest.getVerifyKey(), updatedGuestFromDB.getVerifyKey());
        assertEquals(guest.getVerified(), updatedGuestFromDB.getVerified());
        assertEquals(guest.getGuestStatus().getId(), updatedGuestFromDB.getGuestStatus().getId());
        assertEquals(guest.getUserAccount().getId(), updatedGuestFromDB.getUserAccount().getId());

        assertEquals(newCredit, updatedGuestFromDB.getCredit());

        assertEquals(guest.getCreated(), updatedGuestFromDB.getCreated());

        assertTrue(updatedGuestFromDB.getUpdated().getTime() >= guest.getUpdated().getTime());

        final IUserAccount updatedUserAccountFromDB = updatedGuestFromDB.getUserAccount();
        assertEquals(userAccount.getId(), updatedUserAccountFromDB.getId());

        assertEquals(newEmail, updatedUserAccountFromDB.getEmail());
        assertEquals(userAccount.getPassword(), updatedUserAccountFromDB.getPassword());
        assertEquals(userAccount.getRole(), updatedUserAccountFromDB.getRole());
        assertEquals(userAccount.getFirstName(), updatedUserAccountFromDB.getFirstName());
        assertEquals(userAccount.getLastName(), updatedUserAccountFromDB.getLastName());
        assertEquals(userAccount.getBirthday(), updatedUserAccountFromDB.getBirthday());
        assertEquals(userAccount.getAddress(), updatedUserAccountFromDB.getAddress());
        assertEquals(userAccount.getPhone(), updatedUserAccountFromDB.getPhone());
        assertEquals(userAccount.getCreated(), updatedUserAccountFromDB.getCreated());

        assertTrue(updatedUserAccountFromDB.getUpdated().getTime() >= userAccount.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getGuestService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            final IUserAccount userAccount = getNewUserAccount();
            saveNewGuest(userAccount);
        }

        final List<IGuest> allEntities = getGuestService().getAllFullInfo();

        for (final IGuest entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getVerifyKey());
            assertNotNull(entityFromDB.getVerified());
            assertNotNull(entityFromDB.getGuestStatus());
            assertNotNull(entityFromDB.getUserAccount());
            assertNotNull(entityFromDB.getCredit());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IGuest entity = saveNewGuest();
        getGuestService().delete(entity.getId());
        assertNull(getGuestService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewGuest();
        getGuestService().deleteAll();
        assertEquals(0, getGuestService().getAll().size());
    }
}
