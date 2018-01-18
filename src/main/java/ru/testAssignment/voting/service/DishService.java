package ru.testAssignment.voting.service;

import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    Dish update(Dish dish, int menuId) throws NotFoundException;

    Dish create(Dish dish, int menuId);

    void delete(int id, int menuId) throws NotFoundException;

    Dish get(int id, int menuId) throws NotFoundException;

    List<Dish> getAll(int menuId);
}
