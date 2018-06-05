package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.filter.BookedMaintenanceFilter;
import com.itacademy.jd2.po.hotel.dao.api.model.IBookedMaintenance;

public class BookedMaintenanceServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getBookedMaintenanceService().deleteAll();
        getRoomService().deleteAll();
        getBookingStatusService().deleteAll();
        getMaintenanceService().deleteAll();
        getUserAccountService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IBookedMaintenance entity = saveNewBookedMaintenance();

        final IBookedMaintenance entityFromDB = getBookedMaintenanceService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getRoom().getId(), entityFromDB.getRoom().getId());
        assertEquals(entity.getUserAccount().getId(), entityFromDB.getUserAccount().getId());
        assertEquals(entity.getMaintenance().getId(), entityFromDB.getMaintenance().getId());
        assertEquals(entity.getTime(), entityFromDB.getTime());
        assertEquals(entity.getPrice(), entityFromDB.getPrice());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getRoom().getId());
        assertNotNull(entityFromDB.getUserAccount().getId());
        assertNotNull(entityFromDB.getMaintenance().getId());
        assertNotNull(entityFromDB.getTime());
        assertNotNull(entityFromDB.getPrice());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IBookedMaintenance entity = saveNewBookedMaintenance();
        final Double newPrice = getRandomDouble();

        final IBookedMaintenance entityFromDB = getBookedMaintenanceService().getFullInfo(entity.getId());
        entityFromDB.setPrice(newPrice);
        getBookedMaintenanceService().save(entityFromDB);

        final IBookedMaintenance updatedEntityFromDB = getBookedMaintenanceService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());
        assertEquals(entity.getUserAccount().getId(), updatedEntityFromDB.getUserAccount().getId());
        assertEquals(entity.getRoom().getId(), updatedEntityFromDB.getRoom().getId());
        assertEquals(entity.getMaintenance().getId(), updatedEntityFromDB.getMaintenance().getId());
        assertEquals(entity.getTime(), updatedEntityFromDB.getTime());

        assertEquals(newPrice, updatedEntityFromDB.getPrice());

        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getBookedMaintenanceService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewBookedMaintenance();
        }

        final List<IBookedMaintenance> allEntities = getBookedMaintenanceService().getAllFullInfo();

        for (final IBookedMaintenance entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getUserAccount().getId());
            assertNotNull(entityFromDB.getRoom().getId());
            assertNotNull(entityFromDB.getMaintenance().getId());
            assertNotNull(entityFromDB.getTime());
            assertNotNull(entityFromDB.getPrice());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IBookedMaintenance entity = saveNewBookedMaintenance();
        getBookedMaintenanceService().delete(entity.getId());
        assertNull(getBookedMaintenanceService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewBookedMaintenance();
        getBookedMaintenanceService().deleteAll();
        assertEquals(0, getBookedMaintenanceService().getAll().size());
    }

    @Test
    public void simpleFindTest() { // just checks the query syntax and makes
                                   // basic assertions
        for (int i = 0; i < 10; i++) {
            getBookedMaintenanceService().save(saveNewBookedMaintenance());
        }

        final BookedMaintenanceFilter bookedMaintenanceFilter = new BookedMaintenanceFilter();
        bookedMaintenanceFilter.setLimit(3);
        bookedMaintenanceFilter.setSortColumn("price");
        bookedMaintenanceFilter.setSortOrder(Boolean.FALSE);
        List<IBookedMaintenance> all = getBookedMaintenanceService().find(bookedMaintenanceFilter);
        assertEquals(3, all.size());

        bookedMaintenanceFilter.setFetchRoom(true);
        bookedMaintenanceFilter.setFetchUserAccount(true);
        bookedMaintenanceFilter.setFetchMaintenance(true);
        all = getBookedMaintenanceService().find(bookedMaintenanceFilter);
        assertEquals(3, all.size());
        for (final IBookedMaintenance bookedMaintenance : all) {
            assertNotNull(bookedMaintenance.getRoom().getNumber());
            assertNotNull(bookedMaintenance.getUserAccount().getLastName());
            assertNotNull(bookedMaintenance.getMaintenance().getName());
        }
    }
}
