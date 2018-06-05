package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IPost;

public class PostServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getPostService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IPost entity = saveNewPost();

        final IPost entityFromDB = getPostService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getName(), entityFromDB.getName());
        assertEquals(entity.getDescription(), entityFromDB.getDescription());
        if ((entity.getSupervisor() != null) && (entityFromDB.getSupervisor() != null)) {
            assertEquals(entity.getSupervisor().getId(), entityFromDB.getSupervisor().getId());
        }
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getName());
        assertNotNull(entityFromDB.getDescription());
        // assertNotNull(entityFromDB.getSupervisor().getId());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());
        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IPost entity = saveNewPost();
        final String newName = "newName-" + getRandomInt();

        final IPost entityFromDB = getPostService().getFullInfo(entity.getId());
        entityFromDB.setName(newName);
        getPostService().save(entityFromDB);

        final IPost updatedEntityFromDB = getPostService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());

        assertEquals(newName, updatedEntityFromDB.getName());

        assertEquals(entity.getDescription(), updatedEntityFromDB.getDescription());
        if ((entity.getSupervisor() != null) && (updatedEntityFromDB.getSupervisor() != null)) {
            assertEquals(entity.getSupervisor().getId(), updatedEntityFromDB.getSupervisor().getId());
        }

        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getPostService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewPost();
        }

        final List<IPost> allEntities = getPostService().getAllFullInfo();

        for (final IPost entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getName());
            assertNotNull(entityFromDB.getDescription());
            // assertNotNull(entityFromDB.getSupervisor().getId());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IPost entity = saveNewPost();
        getPostService().delete(entity.getId());
        assertNull(getPostService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewPost();
        getPostService().deleteAll();
        assertEquals(0, getPostService().getAll().size());
    }

}
