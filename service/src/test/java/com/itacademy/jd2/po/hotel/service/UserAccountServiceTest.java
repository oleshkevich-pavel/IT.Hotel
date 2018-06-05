package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class UserAccountServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getUserAccountService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IUserAccount entity = saveNewUserAccount();

        final IUserAccount entityFromDB = getUserAccountService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getEmail(), entityFromDB.getEmail());
        assertEquals(entity.getPassword(), entityFromDB.getPassword());
        assertEquals(entity.getRole(), entityFromDB.getRole());
        assertEquals(entity.getFirstName(), entityFromDB.getFirstName());
        assertEquals(entity.getLastName(), entityFromDB.getLastName());
        assertEquals(entity.getBirthday(), entityFromDB.getBirthday());
        assertEquals(entity.getAddress(), entityFromDB.getAddress());
        assertEquals(entity.getPhone(), entityFromDB.getPhone());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getEmail());
        assertNotNull(entityFromDB.getPassword());
        assertNotNull(entityFromDB.getRole());
        assertNotNull(entityFromDB.getFirstName());
        assertNotNull(entityFromDB.getLastName());
        assertNotNull(entityFromDB.getBirthday());
        assertNotNull(entityFromDB.getAddress());
        assertNotNull(entityFromDB.getPhone());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IUserAccount entity = saveNewUserAccount();
        final String newEmail = "newEmail-" + getRandomInt();

        final IUserAccount entityFromDB = getUserAccountService().getFullInfo(entity.getId());
        entityFromDB.setEmail(newEmail);
        getUserAccountService().save(entityFromDB);

        final IUserAccount updatedEntityFromDB = getUserAccountService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());

        assertEquals(newEmail, updatedEntityFromDB.getEmail());

        assertEquals(entity.getPassword(), updatedEntityFromDB.getPassword());
        assertEquals(entity.getRole(), updatedEntityFromDB.getRole());
        assertEquals(entity.getFirstName(), updatedEntityFromDB.getFirstName());
        assertEquals(entity.getLastName(), updatedEntityFromDB.getLastName());
        assertEquals(entity.getBirthday(), updatedEntityFromDB.getBirthday());
        assertEquals(entity.getAddress(), updatedEntityFromDB.getAddress());
        assertEquals(entity.getPhone(), updatedEntityFromDB.getPhone());
        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getUserAccountService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewUserAccount();
        }

        final List<IUserAccount> allEntities = getUserAccountService().getAllFullInfo();

        for (final IUserAccount entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getEmail());
            assertNotNull(entityFromDB.getPassword());
            assertNotNull(entityFromDB.getRole());
            assertNotNull(entityFromDB.getFirstName());
            assertNotNull(entityFromDB.getLastName());
            assertNotNull(entityFromDB.getBirthday());
            assertNotNull(entityFromDB.getAddress());
            assertNotNull(entityFromDB.getPhone());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IUserAccount entity = saveNewUserAccount();
        getUserAccountService().delete(entity.getId());
        assertNull(getUserAccountService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewUserAccount();
        getUserAccountService().deleteAll();
        assertEquals(0, getUserAccountService().getAll().size());
    }
}
