package ru.testAssignment.voting.repository.dish;

import ru.testAssignment.voting.model.Dish;

import java.util.List;

public interface DishRepository {

    Dish save(Dish dish, int restaurantId);

    boolean delete(int id, int restaurantId);

    Dish get(int id, int restaurantId);

    List<Dish> getAll(int restaurantId);

    default Dish getWithRestaurant(int id, int restaurantId) {
        throw new UnsupportedOperationException();
    }
}
