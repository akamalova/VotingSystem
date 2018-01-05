package ru.testAssignment.voting.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testAssignment.voting.model.Restaurant;

import static ru.testAssignment.voting.RestaurantTestData.*;
import static ru.testAssignment.voting.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-mvc.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceImplTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void update() throws Exception {
    }

    @Test
    public void create() throws Exception {
        Restaurant created = getCreated();
        service.create( created, RESTAURANT_ID);
        assertMatch(service.getAll(), created, RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5);
    }

    @Test
    public void delete() throws Exception {
        service.delete(RESTAURANT_ID, USER_ID);
        assertMatch(service.getAll(), RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT5);
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

}