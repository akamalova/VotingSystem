package ru.testAssignment.voting.service;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.testAssignment.voting.model.Restaurant;

import static ru.testAssignment.voting.RestaurantTestData.*;

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
        service.update(updated);
    }

    @Test
    public void create() throws Exception {
        Restaurant created = getCreatedRestaurant();
        service.create(created);
        assertMatch(service.getAll(), created, RESTAURANT5, RESTAURANT1, RESTAURANT3, RESTAURANT4, RESTAURANT2);
    }

    @Test
    public void delete() throws Exception {
        service.delete(RESTAURANT_ID);
        assertMatch(service.getAll(), RESTAURANT5, RESTAURANT3, RESTAURANT4, RESTAURANT2);
    }

    @Test
    public void get() throws Exception {
        Restaurant actual = service.get(RESTAURANT_ID);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(), RESTAURANT5, RESTAURANT1, RESTAURANT3, RESTAURANT4, RESTAURANT2);
    }
}