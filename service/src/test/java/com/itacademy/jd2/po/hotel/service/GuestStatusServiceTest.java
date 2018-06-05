package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IGuestStatus;

public class GuestStatusServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getGuestStatusService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IGuestStatus entity = saveNewGuestStatus();

        final IGuestStatus entityFromDB = getGuestStatusService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getName(), entityFromDB.getName());
        assertEquals(entity.getDiscount(), entityFromDB.getDiscount());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getName());
        assertNotNull(entityFromDB.getDiscount());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IGuestStatus entity = saveNewGuestStatus();
        final String newName = "new-Name-" + getRandomInt();

        final IGuestStatus entityFromDB = getGuestStatusService().getFullInfo(entity.getId());
        entityFromDB.setName(newName);
        getGuestStatusService().save(entityFromDB);

        final IGuestStatus udpatedEntityFromDB = getGuestStatusService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(newName, udpatedEntityFromDB.getName());

        assertEquals(entity.getDiscount(), udpatedEntityFromDB.getDiscount());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(udpatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getGuestStatusService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewGuestStatus();
        }

        final List<IGuestStatus> allEntities = getGuestStatusService().getAllFullInfo();

        for (final IGuestStatus entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getName());
            assertNotNull(entityFromDB.getDiscount());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IGuestStatus entity = saveNewGuestStatus();
        getGuestStatusService().delete(entity.getId());
        assertNull(getGuestStatusService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewGuestStatus();
        getGuestStatusService().deleteAll();
        assertEquals(0, getGuestStatusService().getAll().size());
    }

}
