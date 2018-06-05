package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IBooking;

public class BookingServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getBookingService().deleteAll();
        getBookedMaintenanceService().deleteAll();
        getMaintenanceService().deleteAll();
        getRoomService().deleteAll();
        getBookingStatusService().deleteAll();
        getUserAccountService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IBooking entity = saveNewBooking();

        final IBooking entityFromDB = getBookingService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getRoom().getId(), entityFromDB.getRoom().getId());
        assertEquals(entity.getUserAccount().getId(), entityFromDB.getUserAccount().getId());
        assertEquals(entity.getCheckIn(), entityFromDB.getCheckIn());
        assertEquals(entity.getCheckOut(), entityFromDB.getCheckOut());
        assertEquals(entity.getRoomPrice(), entityFromDB.getRoomPrice());
        assertEquals(entity.getBookingStatus().getId(), entityFromDB.getBookingStatus().getId());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getRoom().getId());
        assertNotNull(entityFromDB.getUserAccount().getId());
        assertNotNull(entityFromDB.getCheckIn());
        assertNotNull(entityFromDB.getCheckOut());
        assertNotNull(entityFromDB.getRoomPrice());
        assertNotNull(entityFromDB.getBookingStatus().getId());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IBooking entity = saveNewBooking();
        final Double newRoomPrice = getRandomDouble();

        final IBooking entityFromDB = getBookingService().getFullInfo(entity.getId());
        entityFromDB.setRoomPrice(newRoomPrice);
        getBookingService().save(entityFromDB);

        final IBooking updatedEntityFromDB = getBookingService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());
        assertEquals(entity.getRoom().getId(), updatedEntityFromDB.getRoom().getId());
        assertEquals(entity.getUserAccount().getId(), updatedEntityFromDB.getUserAccount().getId());
        assertEquals(entity.getCheckIn(), entityFromDB.getCheckIn());
        assertEquals(entity.getCheckOut(), entityFromDB.getCheckOut());

        assertEquals(newRoomPrice, updatedEntityFromDB.getRoomPrice());

        assertEquals(entity.getBookingStatus().getId(), updatedEntityFromDB.getBookingStatus().getId());
        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getBookingService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewBooking();
        }

        final List<IBooking> allEntities = getBookingService().getAllFullInfo();

        for (final IBooking entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getRoom().getId());
            assertNotNull(entityFromDB.getUserAccount().getId());
            assertNotNull(entityFromDB.getCheckIn());
            assertNotNull(entityFromDB.getCheckOut());
            assertNotNull(entityFromDB.getRoomPrice());
            assertNotNull(entityFromDB.getBookingStatus());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IBooking entity = saveNewBooking();
        getBookingService().delete(entity.getId());
        assertNull(getBookingService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewBooking();
        getBookingService().deleteAll();
        assertEquals(0, getBookingService().getAll().size());
    }

}
