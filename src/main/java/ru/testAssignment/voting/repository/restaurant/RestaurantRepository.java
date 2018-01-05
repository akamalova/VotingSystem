package ru.testAssignment.voting.repository.restaurant;

import ru.testAssignment.voting.model.Restaurant;
import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant, int userId);

    boolean delete(int id, int userId);

    Restaurant get(int id);

    List<Restaurant> getAll();
}
