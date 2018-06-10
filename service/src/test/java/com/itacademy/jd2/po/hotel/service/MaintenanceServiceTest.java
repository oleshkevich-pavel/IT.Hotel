package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IMaintenance;

public class MaintenanceServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getMaintenanceService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IMaintenance entity = saveNewMaintenance();

        final IMaintenance entityFromDB = getMaintenanceService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getName(), entityFromDB.getName());
        assertEquals(entity.getActualPrice(), entityFromDB.getActualPrice());
        assertEquals(entity.getPhotoLink(), entityFromDB.getPhotoLink());
        assertEquals(entity.isAvailable(), entityFromDB.isAvailable());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getName());
        assertNotNull(entityFromDB.getActualPrice());
        assertNotNull(entityFromDB.isAvailable());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IMaintenance entity = saveNewMaintenance();
        final String newName = "new-Name-" + getRandomInt();

        final IMaintenance entityFromDB = getMaintenanceService().getFullInfo(entity.getId());
        entityFromDB.setName(newName);
        getMaintenanceService().save(entityFromDB);

        final IMaintenance updatedEntityFromDB = getMaintenanceService().getFullInfo(entityFromDB.getId());
        assertEquals(newName, updatedEntityFromDB.getName());

        assertEquals(entity.getActualPrice(), updatedEntityFromDB.getActualPrice());
        assertEquals(entity.getPhotoLink(), entityFromDB.getPhotoLink());
        assertEquals(entity.isAvailable(), updatedEntityFromDB.isAvailable());
        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getMaintenanceService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewMaintenance();
        }

        final List<IMaintenance> allEntities = getMaintenanceService().getAllFullInfo();

        for (final IMaintenance entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getName());
            assertNotNull(entityFromDB.getActualPrice());
            assertNotNull(entityFromDB.isAvailable());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IMaintenance entity = saveNewMaintenance();
        getMaintenanceService().delete(entity.getId());
        assertNull(getMaintenanceService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewMaintenance();
        getMaintenanceService().deleteAll();
        assertEquals(0, getMaintenanceService().getAll().size());
    }
}
