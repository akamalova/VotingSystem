package ru.testAssignment.voting.service;

import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.repository.RestaurantRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository repository;

    @Override
    public Restaurant update(Restaurant restaurant, int userId) throws NotFoundException {
        return repository.save(restaurant, userId);
    }

    @Override
    public Restaurant create(Restaurant restaurant, int userId) {
        return repository.save(restaurant, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        repository.delete(id, userId);
    }

    @Override
    public Restaurant get(int id, int userId) throws NotFoundException {
        return repository.get(id, userId);
    }

    @Override
    public List<Restaurant> getAll(int userId) {
        return repository.getAllRestaurantsOfHistory(userId);
    }

    @Override
    public List<Restaurant> getByDate(LocalDate date, int userId) {
        return repository.getbyDate(date, userId);
    }
}
