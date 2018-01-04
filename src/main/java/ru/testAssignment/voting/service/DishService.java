package ru.testAssignment.voting.service;

import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.util.List;


public interface DishService {

    Dish update(Dish dish, int restaurantId) throws NotFoundException;

    Dish create(Dish dish, int restaurantId);

    void delete(int id, int restaurantId) throws NotFoundException;

    Dish get(int id, int restaurantId) throws NotFoundException;

    List<Dish> getAll(int restaurantId);

    Dish getWithRestaurant(int id, int restaurantId);
}
