package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IPhotoLink;

public class PhotoLinkServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getPhotoLinkService().deleteAll();
        getRoomService().deleteAll();
        getBookingStatusService().deleteAll();
        getUserAccountService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IPhotoLink entity = saveNewPhotoLink();

        final IPhotoLink entityFromDB = getPhotoLinkService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getUserAccount().getId(), entityFromDB.getUserAccount().getId());
        assertEquals(entity.getRoom().getId(), entityFromDB.getRoom().getId());
        assertEquals(entity.getLink(), entityFromDB.getLink());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getUserAccount().getId());
        assertNotNull(entityFromDB.getRoom().getId());
        assertNotNull(entityFromDB.getLink());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());
        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IPhotoLink entity = saveNewPhotoLink();
        final String newLink = "newLink-" + getRandomInt();

        final IPhotoLink entityFromDB = getPhotoLinkService().getFullInfo(entity.getId());
        entityFromDB.setLink(newLink);
        getPhotoLinkService().save(entityFromDB);

        final IPhotoLink updatedEntityFromDB = getPhotoLinkService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());
        assertEquals(entity.getUserAccount().getId(), updatedEntityFromDB.getUserAccount().getId());
        assertEquals(entity.getRoom().getId(), updatedEntityFromDB.getRoom().getId());

        assertEquals(newLink, updatedEntityFromDB.getLink());

        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getPhotoLinkService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewPhotoLink();
        }

        final List<IPhotoLink> allEntities = getPhotoLinkService().getAllFullInfo();

        for (final IPhotoLink entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getUserAccount().getId());
            assertNotNull(entityFromDB.getRoom().getId());
            assertNotNull(entityFromDB.getLink());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IPhotoLink entity = saveNewPhotoLink();
        getPhotoLinkService().delete(entity.getId());
        assertNull(getPhotoLinkService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewPhotoLink();
        getPhotoLinkService().deleteAll();
        assertEquals(0, getPhotoLinkService().getAll().size());
    }

}
