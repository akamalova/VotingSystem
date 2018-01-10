package ru.testAssignment.voting.repository.dish;

import ru.testAssignment.voting.model.Dish;

import java.util.List;

public interface DishRepository {

    Dish save(Dish dish, int menuId, int userId);

    boolean delete(int id, int menuId, int userId);

    Dish get(int id, int menuId);

    List<Dish> getAll(int menuId);

    default Dish getWithMenu(int id) {
        throw new UnsupportedOperationException();
    }
}
