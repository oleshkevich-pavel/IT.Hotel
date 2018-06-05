package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IBookingStatus;

public class BookingStatusServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getBookingStatusService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IBookingStatus entity = saveNewBookingStatus();

        final IBookingStatus entityFromDB = getBookingStatusService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getName(), entityFromDB.getName());
        assertEquals(entity.getColor(), entityFromDB.getColor());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getName());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IBookingStatus entity = saveNewBookingStatus();
        final String newName = "new-name-" + getRandomInt();

        final IBookingStatus entityFromDB = getBookingStatusService().getFullInfo(entity.getId());
        entityFromDB.setName(newName);
        getBookingStatusService().save(entityFromDB);

        final IBookingStatus updatedEntityFromDB = getBookingStatusService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());

        assertEquals(newName, updatedEntityFromDB.getName());

        assertEquals(entity.getColor(), updatedEntityFromDB.getColor());
        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getBookingStatusService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewBookingStatus();
        }

        final List<IBookingStatus> allEntities = getBookingStatusService().getAllFullInfo();

        for (final IBookingStatus entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getName());
            assertNotNull(entityFromDB.getColor());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IBookingStatus entity = saveNewBookingStatus();
        getBookingStatusService().delete(entity.getId());
        assertNull(getBookingStatusService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewBookingStatus();
        getBookingStatusService().deleteAll();
        assertEquals(0, getBookingStatusService().getAll().size());
    }

}
