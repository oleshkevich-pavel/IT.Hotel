package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.itacademy.jd2.po.hotel.dao.api.model.IMessage;

public class MessageServiceTest extends AbstractTest {

    @Autowired
    private IEmailSenderService emailSenderService;

    @After
    @Before
    public void cleanTables() throws MessagingException {
        getMessageService().deleteAll();
        getMessageService().setEmailSenderService(getSpy());
    }

    private IEmailSenderService getSpy() throws MessagingException {
        final IEmailSenderService spy = Mockito.spy(emailSenderService);
        // let's make clear() do nothing
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public final Void answer(final InvocationOnMock invocation) throws Throwable {
                System.out.println("email sending simulated");
                return null;
            }
        }).when(spy).sendEmailFromWebSite(Mockito.isA(IMessage.class));
        return spy;
    }

    @Test
    public void testCreate() throws MessagingException {
        final IMessage entity = saveNewMessage();

        final IMessage entityFromDB = getMessageService().get(entity.getId());

        assertEquals(entity.getId(), entityFromDB.getId());
        assertEquals(entity.getName(), entityFromDB.getName());
        assertEquals(entity.getPhone(), entityFromDB.getPhone());
        assertEquals(entity.getEmail(), entityFromDB.getEmail());
        assertEquals(entity.getMessage(), entityFromDB.getMessage());

        assertEquals(entity.getCreated(), entityFromDB.getCreated());
        assertEquals(entity.getUpdated(), entityFromDB.getUpdated());

        assertNotNull(entityFromDB.getId());
        assertNotNull(entityFromDB.getName());
        assertNotNull(entityFromDB.getPhone());
        assertNotNull(entityFromDB.getEmail());
        assertNotNull(entityFromDB.getMessage());

        assertNotNull(entityFromDB.getCreated());
        assertNotNull(entityFromDB.getUpdated());

        assertTrue(entityFromDB.getCreated().equals(entityFromDB.getUpdated()));
    }

    @Test
    public void testUpdate() throws MessagingException {
        final IMessage entity = saveNewMessage();
        final String newName = "newName-" + getRandomInt();

        final IMessage entityFromDB = getMessageService().get(entity.getId());
        entityFromDB.setName(newName);
        getMessageService().save(entityFromDB);

        final IMessage updatedEntityFromDB = getMessageService().get(entityFromDB.getId());
        assertEquals(entity.getId(), updatedEntityFromDB.getId());

        assertEquals(newName, updatedEntityFromDB.getName());

        assertEquals(entity.getPhone(), updatedEntityFromDB.getPhone());
        assertEquals(entity.getEmail(), updatedEntityFromDB.getEmail());
        assertEquals(entity.getMessage(), updatedEntityFromDB.getMessage());

        assertEquals(entity.getCreated(), updatedEntityFromDB.getCreated());
        assertTrue(updatedEntityFromDB.getUpdated().getTime() >= entity.getUpdated().getTime());
    }

    @Test
    public void testGetAll() throws MessagingException {
        final int initialCount = getMessageService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewMessage();
        }

        final List<IMessage> allEntities = getMessageService().getAll();

        for (final IMessage entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getName());
            assertNotNull(entityFromDB.getPhone());
            assertNotNull(entityFromDB.getEmail());
            assertNotNull(entityFromDB.getMessage());

            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() throws MessagingException {
        final IMessage entity = saveNewMessage();
        getMessageService().delete(entity.getId());
        assertNull(getMessageService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() throws MessagingException {
        saveNewMessage();
        getMessageService().deleteAll();
        assertEquals(0, getMessageService().getAll().size());
    }
}
