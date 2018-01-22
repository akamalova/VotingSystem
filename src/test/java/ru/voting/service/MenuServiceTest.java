package ru.voting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.model.Menu;
import ru.voting.util.exception.NotFoundException;

import java.time.LocalDate;

import static ru.voting.MenuTestData.*;
import static ru.voting.RestaurantTestData.RESTAURANT_ID;

public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Test
    public void update() throws Exception {
        Menu updated = getUpdatedMenu();
        service.update(updated, RESTAURANT_ID);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + MENU_ID);
        service.update(MENU1, RESTAURANT_ID + 1);
    }

    @Test
    public void create() throws Exception {
        Menu created = getCreatedMenu();
        service.create(created, RESTAURANT_ID);
        assertMatch(service.getAll(RESTAURANT_ID), created, MENU1, MENU2);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MENU_ID, RESTAURANT_ID);
        assertMatch(service.getAll(RESTAURANT_ID), MENU2);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(MENU_ID, RESTAURANT_ID + 1);
    }


    @Test
    public void get() throws Exception {
        Menu actual = service.get(MENU_ID, RESTAURANT_ID);
        assertMatch(actual, MENU1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(RESTAURANT_ID), MENU1, MENU2);
    }

    @Test
    public void getByDate() throws Exception {
        assertMatch(service.getByDate(LocalDate.of(2017, 5, 30)), MENU1);
    }

}