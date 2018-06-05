package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IComment;

public class CommentTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getCommentService().deleteAll();
        getRoomService().deleteAll();
        getBookingStatusService().deleteAll();
        getUserAccountService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IComment entity = saveNewComment();

        final IComment entityFromDB = getCommentService().getFullInfo(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getUserAccount().getId(), entityFromDB.getUserAccount().getId());
        assertEquals(entity.getRoom().getId(), entityFromDB.getRoom().getId());
        assertEquals(entity.getComment(), entityFromDB.getComment());
        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getUserAccount().getId());
        assertNotNull(entityFromDB.getRoom().getId());
        assertNotNull(entityFromDB.getComment());
        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());
        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() {
        final IComment entity = saveNewComment();
        final String newComment = "newComment-" + getRandomInt();

        final IComment entityFromDB = getCommentService().getFullInfo(entity.getId());
        entityFromDB.setComment(newComment);
        getCommentService().save(entityFromDB);

        final IComment updatedEntityFromDB = getCommentService().getFullInfo(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());
        assertEquals(entity.getUserAccount().getId(), updatedEntityFromDB.getUserAccount().getId());
        assertEquals(entity.getRoom().getId(), updatedEntityFromDB.getRoom().getId());

        assertEquals(newComment, updatedEntityFromDB.getComment());

        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getCommentService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewComment();
        }

        final List<IComment> allEntities = getCommentService().getAllFullInfo();

        for (final IComment entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getUserAccount().getId());
            assertNotNull(entityFromDB.getRoom().getId());
            assertNotNull(entityFromDB.getComment());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IComment entity = saveNewComment();
        getCommentService().delete(entity.getId());
        assertNull(getCommentService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewComment();
        getCommentService().deleteAll();
        assertEquals(0, getCommentService().getAll().size());
    }

}
