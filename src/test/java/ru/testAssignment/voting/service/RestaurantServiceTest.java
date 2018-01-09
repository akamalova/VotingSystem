package ru.testAssignment.voting.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testAssignment.voting.Profiles;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.repository.restaurant.RestaurantRepository;

import static ru.testAssignment.voting.RestaurantTestData.*;
import static ru.testAssignment.voting.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-mvc.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    public void update() throws Exception {
    }

    @Test
    public void create() throws Exception {
        Restaurant created = getCreated();
        repository.save( created, RESTAURANT_ID);
        assertMatch(repository.getAll(), created, RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5);
    }

    @Test
    public void delete() throws Exception {
        repository.delete(RESTAURANT_ID, USER_ID);
        assertMatch(repository.getAll(), RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT5);
    }

    @Test
    public void get() throws Exception {
        Restaurant actual = repository.get(RESTAURANT_ID);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(repository.getAll(), RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5);
    }

}