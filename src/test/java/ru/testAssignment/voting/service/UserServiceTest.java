package ru.testAssignment.voting.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.testAssignment.voting.model.Role;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.to.UserTo;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collections;
import static ru.testAssignment.voting.UserTestData.*;
import static ru.testAssignment.voting.util.UserUtil.asTo;
import static ru.testAssignment.voting.util.UserUtil.updateFromTo;



public class UserServiceTest extends AbstractServiceTest{

    @Autowired
    protected UserService service;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", true, LocalDate.now(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        service.getAll().forEach(System.out::println);
        newUser.setId(created.getId());
        assertMatch(service.getAll(), ADMIN, USER2, newUser, USER1);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void updateWithTo() throws Exception {
        UserTo updatedTo = asTo(USER2);
        updatedTo.setName("UpdatedName");
        service.update(updatedTo);
        User user = new User(USER2);
        assertMatch(service.get(USER_ID + 1), updateFromTo(user, updatedTo));
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID);
        assertMatch(service.getAll(), ADMIN, USER2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(), ADMIN, USER2, USER1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    public void getByDate() throws Exception {
        assertMatch(service.getByDate(LocalDate.of(2014,5,30)), USER2, ADMIN);
    }

    @Test
    public void testEnable() {
        service.enable(USER_ID, false);
        Assert.assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        Assert.assertTrue(service.get(USER_ID).isEnabled());
    }

}