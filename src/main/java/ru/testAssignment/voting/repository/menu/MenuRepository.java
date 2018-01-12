package ru.testAssignment.voting.repository.menu;


import ru.testAssignment.voting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {

    Menu save(Menu menu, int restaurantId, int userId);

    boolean delete(int id, int restaurantId, int userId);

    Menu get(int id);

    List<Menu> getAll(int restaurantId);

    List<Menu> getByDate(LocalDate date);

    default Menu getWithRestaurant(int id) {
        throw new UnsupportedOperationException();
    }
}
