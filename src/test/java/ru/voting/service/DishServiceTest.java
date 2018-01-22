package ru.voting.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.model.Dish;
import ru.voting.util.exception.NotFoundException;
import static ru.voting.DishTestData.*;
import static ru.voting.MenuTestData.MENU_ID;
public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;


    @Test
    public void update() throws Exception {
        Dish updated = getUpdatedDish();
        service.update(updated, MENU_ID);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + DISH_ID);
        service.update(DISH1, MENU_ID + 1);
    }

    @Test
    public void create() throws Exception {
        Dish created = getCreatedDish();
        service.create(created, MENU_ID);
        assertMatch(service.getAll(MENU_ID), DISH1, DISH2, created);
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH_ID, MENU_ID);
        assertMatch(service.getAll(MENU_ID), DISH2);
    }

    @Test

    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(DISH_ID, MENU_ID + 1);
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
}