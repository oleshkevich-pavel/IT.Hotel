package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.ITask;

public class TaskServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getTaskService().deleteAll();
        getEmployeeService().deleteAll();
    }

    @Test
    public void testCreate() {
        final ITask entity = saveNewTask();

        final ITask entityFromDB = getTaskService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getToDo(), entityFromDB.getToDo());
        assertEquals(entity.getDescription(), entityFromDB.getDescription());
        assertEquals(entity.getPriority(), entityFromDB.getPriority());
        assertEquals(entity.getExecutionTime(), entityFromDB.getExecutionTime());
        assertEquals(entity.getAnswerable().getId(), entityFromDB.getAnswerable().getId());
        assertEquals(entity.getExecuted(), entityFromDB.getExecuted());
        assertEquals(entity.getCreator().getId(), entityFromDB.getCreator().getId());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getToDo());
        assertNotNull(entityFromDB.getDescription());
        assertNotNull(entityFromDB.getPriority());
        assertNotNull(entityFromDB.getExecutionTime());
        assertNotNull(entityFromDB.getAnswerable().getId());
        assertNotNull(entityFromDB.getExecuted());
        assertNotNull(entityFromDB.getCreator().getId());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final ITask entity = saveNewTask();
        final String newToDo = "newToDo-" + getRandomInt();

        final ITask entityFromDB = getTaskService().getFullInfo(entity.getId());
        entityFromDB.setToDo(newToDo);
        getTaskService().save(entityFromDB);

        final ITask updatedEntityFromDB = getTaskService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());

        assertEquals(newToDo, updatedEntityFromDB.getToDo());

        assertEquals(entity.getDescription(), updatedEntityFromDB.getDescription());
        assertEquals(entity.getPriority(), updatedEntityFromDB.getPriority());
        assertEquals(entity.getExecutionTime(), updatedEntityFromDB.getExecutionTime());
        assertEquals(entity.getAnswerable().getId(), entityFromDB.getAnswerable().getId());
        assertEquals(entity.getExecuted(), entityFromDB.getExecuted());
        assertEquals(entity.getCreator().getId(), entityFromDB.getCreator().getId());

        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getTaskService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewTask();
        }

        final List<ITask> allEntities = getTaskService().getAllFullInfo();

        for (final ITask entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getToDo());
            assertNotNull(entityFromDB.getDescription());
            assertNotNull(entityFromDB.getPriority());
            assertNotNull(entityFromDB.getExecutionTime());
            assertNotNull(entityFromDB.getAnswerable().getId());
            assertNotNull(entityFromDB.getExecuted());
            assertNotNull(entityFromDB.getCreator().getId());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final ITask entity = saveNewTask();
        getTaskService().delete(entity.getId());
        assertNull(getTaskService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewTask();
        getTaskService().deleteAll();
        assertEquals(0, getTaskService().getAll().size());
    }

}
