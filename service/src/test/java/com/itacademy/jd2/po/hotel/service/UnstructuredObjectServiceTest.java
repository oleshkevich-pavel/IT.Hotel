package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IUnstructuredObject;

public class UnstructuredObjectServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getUnstructuredObjectService().deleteAll();
        getGuestService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IUnstructuredObject entity = saveNewUnstructuredObject();

        final IUnstructuredObject entityFromDB = getUnstructuredObjectService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getName(), entityFromDB.getName());
        assertArrayEquals(entity.getContent(), entityFromDB.getContent());
        assertEquals(entity.getUserAccount().getId(), entityFromDB.getUserAccount().getId());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getName());
        assertNotNull(entityFromDB.getContent());
        assertNotNull(entityFromDB.getUserAccount().getId());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IUnstructuredObject entity = saveNewUnstructuredObject();
        final String newName = "newName-" + getRandomInt();

        final IUnstructuredObject entityFromDB = getUnstructuredObjectService().getFullInfo(entity.getId());
        entityFromDB.setName(newName);
        getUnstructuredObjectService().save(entityFromDB);

        final IUnstructuredObject updatedEntityFromDB = getUnstructuredObjectService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());

        assertEquals(newName, updatedEntityFromDB.getName());

        assertArrayEquals(entity.getContent(), updatedEntityFromDB.getContent());
        assertEquals(entity.getUserAccount().getId(), entityFromDB.getUserAccount().getId());

        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getUnstructuredObjectService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewUnstructuredObject();
        }

        final List<IUnstructuredObject> allEntities = getUnstructuredObjectService().getAllFullInfo();

        for (final IUnstructuredObject entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getName());
            assertNotNull(entityFromDB.getContent());
            assertNotNull(entityFromDB.getUserAccount().getId());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IUnstructuredObject entity = saveNewUnstructuredObject();
        getUnstructuredObjectService().delete(entity.getId());
        assertNull(getUnstructuredObjectService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewUnstructuredObject();
        getUnstructuredObjectService().deleteAll();
        assertEquals(0, getUnstructuredObjectService().getAll().size());
    }

}
