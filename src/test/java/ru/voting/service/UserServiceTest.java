package ru.voting.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.voting.UserTestData;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.to.UserTo;
import ru.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collections;

import static ru.voting.util.UserUtil.asTo;
import static ru.voting.util.UserUtil.updateFromTo;


public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", true, LocalDate.now(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        service.getAll().forEach(System.out::println);
        newUser.setId(created.getId());
        UserTestData.assertMatch(service.getAll(), UserTestData.ADMIN, UserTestData.USER2, newUser, UserTestData.USER1);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void update() throws Exception {
        User updated = new User(UserTestData.USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        UserTestData.assertMatch(service.get(UserTestData.USER_ID), updated);
    }

    @Test
    public void updateWithTo() throws Exception {
        UserTo updatedTo = asTo(UserTestData.USER2);
        updatedTo.setName("UpdatedName");
        service.update(updatedTo);
        User user = new User(UserTestData.USER2);
        UserTestData.assertMatch(service.get(UserTestData.USER_ID + 1), updateFromTo(user, updatedTo));
    }

    @Test
    public void delete() throws Exception {
        service.delete(UserTestData.USER_ID);
        UserTestData.assertMatch(service.getAll(), UserTestData.ADMIN, UserTestData.USER2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = service.get(UserTestData.ADMIN_ID);
        UserTestData.assertMatch(user, UserTestData.ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void getAll() throws Exception {
        UserTestData.assertMatch(service.getAll(), UserTestData.ADMIN, UserTestData.USER2, UserTestData.USER1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        UserTestData.assertMatch(user, UserTestData.ADMIN);
    }

    @Test
    public void testEnable() {
        service.enable(UserTestData.USER_ID, false);
        Assert.assertFalse(service.get(UserTestData.USER_ID).isEnabled());
        service.enable(UserTestData.USER_ID, true);
        Assert.assertTrue(service.get(UserTestData.USER_ID).isEnabled());
    }

}