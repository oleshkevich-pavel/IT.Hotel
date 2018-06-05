package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IRoom;

public class RoomServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getRoomService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IRoom entity = saveNewRoom();

        final IRoom entityFromDB = getRoomService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getFloor(), entityFromDB.getFloor());
        assertEquals(entity.getType(), entityFromDB.getType());
        assertEquals(entity.getAccomodation(), entityFromDB.getAccomodation());
        assertEquals(entity.getView(), entityFromDB.getView());
        assertEquals(entity.getActualPrice(), entityFromDB.getActualPrice());
        assertEquals(entity.getDescription(), entityFromDB.getDescription());
        assertEquals(entity.isDirty(), entityFromDB.isDirty());
        assertEquals(entity.isBroken(), entityFromDB.isBroken());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getNumber());
        assertNotNull(entityFromDB.getFloor());
        assertNotNull(entityFromDB.getType());
        assertNotNull(entityFromDB.getAccomodation());
        assertNotNull(entityFromDB.getView());
        assertNotNull(entityFromDB.getActualPrice());
        assertNotNull(entityFromDB.getDescription());
        assertNotNull(entityFromDB.isDirty());
        assertNotNull(entityFromDB.isBroken());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());
        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IRoom entity = saveNewRoom();
        final Integer newNumber = getRandomInt();

        final IRoom entityFromDB = getRoomService().getFullInfo(entity.getId());
        entityFromDB.setNumber(newNumber);
        getRoomService().save(entityFromDB);

        final IRoom updatedEntityFromDB = getRoomService().getFullInfo(entityFromDB.getId());
        assertEquals(newNumber, updatedEntityFromDB.getNumber());
        assertEquals(entity.getFloor(), updatedEntityFromDB.getFloor());
        assertEquals(entity.getType(), updatedEntityFromDB.getType());
        assertEquals(entity.getAccomodation(), updatedEntityFromDB.getAccomodation());
        assertEquals(entity.getView(), updatedEntityFromDB.getView());
        assertEquals(entity.getActualPrice(), updatedEntityFromDB.getActualPrice());
        assertEquals(entity.getDescription(), updatedEntityFromDB.getDescription());
        assertEquals(entity.isDirty(), updatedEntityFromDB.isDirty());
        assertEquals(entity.isBroken(), updatedEntityFromDB.isBroken());
        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getRoomService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewRoom();
        }

        final List<IRoom> allEntities = getRoomService().getAllFullInfo();

        for (final IRoom entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getNumber());
            assertNotNull(entityFromDB.getFloor());
            assertNotNull(entityFromDB.getType());
            assertNotNull(entityFromDB.getAccomodation());
            assertNotNull(entityFromDB.getView());
            assertNotNull(entityFromDB.getActualPrice());
            assertNotNull(entityFromDB.getDescription());
            assertNotNull(entityFromDB.isDirty());
            assertNotNull(entityFromDB.isBroken());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IRoom entity = saveNewRoom();
        getRoomService().delete(entity.getId());
        assertNull(getRoomService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewRoom();
        getRoomService().deleteAll();
        assertEquals(0, getRoomService().getAll().size());
    }

}
