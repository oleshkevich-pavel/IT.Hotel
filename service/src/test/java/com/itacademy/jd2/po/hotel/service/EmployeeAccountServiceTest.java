package com.itacademy.jd2.po.hotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.po.hotel.dao.api.model.IEmployee;
import com.itacademy.jd2.po.hotel.dao.api.model.IUserAccount;

public class EmployeeAccountServiceTest extends AbstractTest {

    @After
    @Before
    public void cleanTables() {
        getEmployeeService().deleteAll();
        getPostService().deleteAll();
    }

    @Test
    public void testCreateWithUserAccount() {
        final IUserAccount userAccount = getNewUserAccount();
        final IEmployee employee = saveNewEmployee(userAccount);

        final IEmployee employeeFromDB = getEmployeeService().getFullInfo(employee.getId());

        assertEquals(employee.getId(), employeeFromDB.getId());
        assertEquals(employee.getHiring(), employeeFromDB.getHiring());
        assertEquals(employee.getLayoff(), employeeFromDB.getLayoff());
        assertEquals(employee.getPost().getId(), employeeFromDB.getPost().getId());
        assertEquals(employee.getUserAccount().getId(), employeeFromDB.getUserAccount().getId());
        assertEquals(employee.getCreated(), employeeFromDB.getCreated());
        assertEquals(employee.getUpdated(), employeeFromDB.getUpdated());

        assertNotNull(employeeFromDB.getId());
        assertNotNull(employeeFromDB.getHiring());
        // assertNotNull(employeeFromDB.getLayoff());
        assertNotNull(employeeFromDB.getPost().getId());
        assertNotNull(employeeFromDB.getUserAccount().getId());
        assertNotNull(employeeFromDB.getCreated());
        assertNotNull(employeeFromDB.getUpdated());

        assertTrue(employeeFromDB.getCreated().equals(employeeFromDB.getUpdated()));

        final IUserAccount userAccountFromDB = employeeFromDB.getUserAccount();
        assertEquals(userAccount.getId(), userAccountFromDB.getId());
        assertEquals(userAccount.getEmail(), userAccountFromDB.getEmail());
        assertEquals(userAccount.getPassword(), userAccountFromDB.getPassword());
        assertEquals(userAccount.getRole(), userAccountFromDB.getRole());
        assertEquals(userAccount.getFirstName(), userAccountFromDB.getFirstName());
        assertEquals(userAccount.getLastName(), userAccountFromDB.getLastName());
        assertEquals(userAccount.getBirthday(), userAccountFromDB.getBirthday());
        assertEquals(userAccount.getAddress(), userAccountFromDB.getAddress());
        assertEquals(userAccount.getPhone(), userAccountFromDB.getPhone());
        assertEquals(userAccount.getCreated(), userAccountFromDB.getCreated());
        assertEquals(userAccount.getUpdated(), userAccountFromDB.getUpdated());

        assertNotNull(userAccountFromDB.getId());
        assertNotNull(userAccountFromDB.getEmail());
        assertNotNull(userAccountFromDB.getPassword());
        assertNotNull(userAccountFromDB.getRole());
        assertNotNull(userAccountFromDB.getFirstName());
        assertNotNull(userAccountFromDB.getLastName());
        assertNotNull(userAccountFromDB.getBirthday());
        assertNotNull(userAccountFromDB.getAddress());
        assertNotNull(userAccountFromDB.getPhone());
        assertNotNull(userAccountFromDB.getCreated());
        assertNotNull(userAccountFromDB.getUpdated());

        assertTrue(userAccountFromDB.getCreated().equals(userAccountFromDB.getUpdated()));
    }

    @Test
    public void testUpdateEmployeeWithUserAccount() {
        final IUserAccount userAccount = getNewUserAccount();
        final IEmployee employee = saveNewEmployee(userAccount);

        final String newEmail = "newEmail-" + getRandomInt();
        final Date newHiring = getRandomDate();

        final IEmployee employeeFromDB = getEmployeeService().getFullInfo(employee.getId());
        final IUserAccount userAccountFromDB = employeeFromDB.getUserAccount();
        userAccountFromDB.setEmail(newEmail);
        employeeFromDB.setHiring(newHiring);

        getEmployeeService().save(userAccountFromDB, employeeFromDB);

        final IEmployee updatedEmployeeFromDB = getEmployeeService().getFullInfo(employee.getId());
        assertEquals(employee.getId(), updatedEmployeeFromDB.getId());

        assertEquals(newHiring, updatedEmployeeFromDB.getHiring());

        assertEquals(employee.getLayoff(), updatedEmployeeFromDB.getLayoff());
        assertEquals(employee.getPost().getId(), updatedEmployeeFromDB.getPost().getId());
        assertEquals(employee.getUserAccount().getId(), updatedEmployeeFromDB.getUserAccount().getId());
        assertEquals(employee.getCreated(), updatedEmployeeFromDB.getCreated());

        assertTrue(updatedEmployeeFromDB.getUpdated().getTime() >= employee.getUpdated().getTime());

        final IUserAccount updatedUserAccountFromDB = updatedEmployeeFromDB.getUserAccount();
        assertEquals(userAccount.getId(), updatedUserAccountFromDB.getId());

        assertEquals(newEmail, updatedUserAccountFromDB.getEmail());

        assertEquals(userAccount.getPassword(), updatedUserAccountFromDB.getPassword());
        assertEquals(userAccount.getRole(), updatedUserAccountFromDB.getRole());
        assertEquals(userAccount.getFirstName(), updatedUserAccountFromDB.getFirstName());
        assertEquals(userAccount.getLastName(), updatedUserAccountFromDB.getLastName());
        assertEquals(userAccount.getBirthday(), updatedUserAccountFromDB.getBirthday());
        assertEquals(userAccount.getAddress(), updatedUserAccountFromDB.getAddress());
        assertEquals(userAccount.getPhone(), updatedUserAccountFromDB.getPhone());
        assertEquals(userAccount.getCreated(), updatedUserAccountFromDB.getCreated());

        assertTrue(updatedUserAccountFromDB.getUpdated().getTime() >= userAccount.getUpdated().getTime());
    }

    @Test
    public void testGetAll() {
        final int initialCount = getEmployeeService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            final IUserAccount userAccount = getNewUserAccount();
            saveNewEmployee(userAccount);
        }

        final List<IEmployee> allEntities = getEmployeeService().getAllFullInfo();

        for (final IEmployee entityFromDB : allEntities) {
            assertNotNull(entityFromDB.getId());
            assertNotNull(entityFromDB.getHiring());
            // assertNotNull(entityFromDB.getLayoff());
            assertNotNull(entityFromDB.getPost());
            assertNotNull(entityFromDB.getUserAccount());
            assertNotNull(entityFromDB.getCreated());
            assertNotNull(entityFromDB.getUpdated());
        }
        assertEquals(randomObjectsCount + initialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IEmployee entity = saveNewEmployee();
        getEmployeeService().delete(entity.getId());
        assertNull(getEmployeeService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewEmployee();
        getEmployeeService().deleteAll();
        assertEquals(0, getEmployeeService().getAll().size());
    }
}
