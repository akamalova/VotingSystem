package ru.testAssignment.voting.repository;

import ru.testAssignment.voting.model.Restaurant;
import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    // null if Restaurant meal do not belong to userId
    Restaurant save(Restaurant restaurant, int userId);

    // false if Restaurant do not belong to userId
    boolean delete(int id, int userId);

    // null if Restaurant do not belong to userId
    Restaurant get(int id, int userId);

    List<Restaurant> getAllRestaurantsOfHistory(int userId);

    List<Restaurant> getbyDate(LocalDate date, int userId);

}
