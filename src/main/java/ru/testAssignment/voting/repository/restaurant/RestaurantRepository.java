package ru.testAssignment.voting.repository.restaurant;

import ru.testAssignment.voting.model.Restaurant;
import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    // null if Restaurant meal do not belong to userId
    Restaurant save(Restaurant restaurant, int userId);

    // false if Restaurant do not belong to userId
    boolean delete(int id, int userId);

    Restaurant get(int id);

    List<Restaurant> getAll();

    List<Restaurant> getbyDate(LocalDate date);

}
