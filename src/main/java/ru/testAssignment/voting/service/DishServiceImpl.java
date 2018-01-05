package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.repository.dish.DishRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository dishRepository;

    @Override
    public Dish update(Dish dish, int restaurantId) throws NotFoundException {
        return dishRepository.save(dish, restaurantId);
    }

    @Override
    public Dish create(Dish dish, int restaurantId) {
        return dishRepository.save(dish, restaurantId);
    }

    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        dishRepository.delete(id, restaurantId);
    }

    @Override
    public Dish get(int id, int restaurantId) throws NotFoundException {
        return dishRepository.get(id, restaurantId);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    @Override
    public Dish getWithRestaurant(int id) {
        return dishRepository.getWithRestaurant(id);
    }
}
