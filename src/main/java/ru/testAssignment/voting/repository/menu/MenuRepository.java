package ru.testAssignment.voting.repository.menu;

import ru.testAssignment.voting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {

    Menu save(Menu menu, int restaurantId);

    boolean delete(int id, int restaurantId);

    Menu get(int id, int restaurantId);

    List<Menu> getAll(int restaurantId);

    List<Menu> getByDate(LocalDate date);
}
