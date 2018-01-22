package ru.voting.service;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.model.Restaurant;
import ru.voting.RestaurantTestData;

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
        Restaurant updated = RestaurantTestData.getUpdatedRestaurant();
        service.update(updated);
    }

    @Test
    public void create() throws Exception {
        Restaurant created = RestaurantTestData.getCreatedRestaurant();
        service.create(created);
        RestaurantTestData.assertMatch(service.getAll(), created, RestaurantTestData.RESTAURANT5, RestaurantTestData.RESTAURANT1, RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT4, RestaurantTestData.RESTAURANT2);
    }

    @Test
    public void delete() throws Exception {
        service.delete(RestaurantTestData.RESTAURANT_ID);
        RestaurantTestData.assertMatch(service.getAll(), RestaurantTestData.RESTAURANT5, RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT4, RestaurantTestData.RESTAURANT2);
    }

    @Test
    public void get() throws Exception {
        Restaurant actual = service.get(RestaurantTestData.RESTAURANT_ID);
        RestaurantTestData.assertMatch(actual, RestaurantTestData.RESTAURANT1);
    }

    @Test
    public void getAll() throws Exception {
        RestaurantTestData.assertMatch(service.getAll(), RestaurantTestData.RESTAURANT5, RestaurantTestData.RESTAURANT1, RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT4, RestaurantTestData.RESTAURANT2);
    }
}