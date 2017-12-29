package ru.testAssignment.voting.repository;

import ru.testAssignment.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    int delete(int id, int userId);

    Restaurant save(Restaurant item);

    List<Restaurant> getAll(int userId);

    List<Restaurant> getByDate(LocalDate date, int userId);

}
