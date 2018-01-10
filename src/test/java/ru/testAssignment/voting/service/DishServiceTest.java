package ru.testAssignment.voting.service;


import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.testAssignment.voting.model.Dish;

import java.util.List;

public class DishServiceTest extends AbstractServiceTest{

    @Autowired
    private DishService service;

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void create() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
        List<Dish> menu = service.getAll(100003);
        menu.forEach(System.out::println);
    }

    @Test
    public void getWithRestaurant() throws Exception {
    }
}