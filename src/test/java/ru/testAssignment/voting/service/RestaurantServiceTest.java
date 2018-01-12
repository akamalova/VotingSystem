package ru.testAssignment.voting.service;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.util.exception.NotFoundException;

import static ru.testAssignment.voting.RestaurantTestData.*;
import static ru.testAssignment.voting.UserTestData.ADMIN_ID;
import static ru.testAssignment.voting.UserTestData.USER_ID;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + RESTAURANT_ID);
        service.update(RESTAURANT1, USER_ID);
    }

    @Test
    public void create() throws Exception {
        Restaurant created = getCreatedRestaurant();
        if (service.create(created, ADMIN_ID) == null) throw new NotFoundException("Not found");
        assertMatch(service.getAll(), created, RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5);
    }

    @Test
    public void notFoundCreate() throws Exception {
        thrown.expect(NotFoundException.class);
        Restaurant created = getCreatedRestaurant();
        if (service.create(created, USER_ID) == null) throw new NotFoundException("Not found");
    }

    @Test
    public void delete() throws Exception {
        service.delete(RESTAURANT_ID, ADMIN_ID);
        assertMatch(service.getAll(), RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT5);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(RESTAURANT_ID, USER_ID);
    }

    @Test
    public void get() throws Exception {
        Restaurant actual = service.get(RESTAURANT_ID);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(), RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5);
    }

    /*@Test
    public void testValidation() throws Exception {
        service.create(new Restaurant(null, "dgf", " "), ADMIN_ID);
        service.getAll().forEach(System.out::println);
        validateRootCause(() -> service.create(new Restaurant(null, "dgf", " "), ADMIN_ID), ConstraintViolationException.class);
        *//*validateRootCause(() -> service.create(new Restaurant(null, "", "descriptionSecond"), ADMIN_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "third", "descriptionThird"), ADMIN_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "fourth", "descriptionFourth"), ADMIN_ID), ConstraintViolationException.class);*//*
    }*/

}