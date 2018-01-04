package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.repository.restaurant.RestaurantRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
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
    public Restaurant get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Restaurant> getByDate(LocalDate date) {
        return repository.getbyDate(date);
    }
}
