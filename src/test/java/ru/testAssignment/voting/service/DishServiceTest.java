package ru.testAssignment.voting.service;



import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.util.exception.NotFoundException;

import static ru.testAssignment.voting.DishTestData.*;
import static ru.testAssignment.voting.MenuTestData.MENU_ID;
import static ru.testAssignment.voting.UserTestData.ADMIN_ID;
import static ru.testAssignment.voting.UserTestData.USER_ID;

public class DishServiceTest extends AbstractServiceTest{

    @Autowired
    private DishService service;


    @Test
    public void update() throws Exception {
        Dish updated = getUpdatedDish();
        service.update(updated, MENU_ID, ADMIN_ID);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + DISH_ID);
        service.update(DISH1, MENU_ID, USER_ID);
    }

    @Test
    public void create() throws Exception {
        Dish created = getCreatedDish();
        if (service.create(created, MENU_ID, ADMIN_ID) == null) throw new NotFoundException("Not found");
        assertMatch(service.getAll(MENU_ID), DISH1, DISH2, created);
    }

    @Test
    public void notFoundCreate() throws Exception {
        thrown.expect(NotFoundException.class);
        Dish created = getCreatedDish();
        if (service.create(created, MENU_ID, USER_ID) == null) throw new NotFoundException("Not found");
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH_ID, MENU_ID, ADMIN_ID);
        assertMatch(service.getAll(MENU_ID), DISH2);
    }
    @Test

    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(DISH_ID, MENU_ID, USER_ID);
    }

    @Test
    public void get() throws Exception {
        Dish actual = service.get(DISH_ID, MENU_ID);
        assertMatch(actual, DISH1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(MENU_ID), DISH1, DISH2);
    }

    /*@Test
    public void getWithMenu() throws Exception {
        System.out.println(service.getWithMenu(DISH_ID));
    }*/
}