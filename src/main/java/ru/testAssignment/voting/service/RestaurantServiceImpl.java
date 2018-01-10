package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.repository.restaurant.RestaurantRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant update(Restaurant restaurant, int userId) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");

        return checkNotFoundWithId(repository.save(restaurant, userId), restaurant.getId());
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant, userId);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("restaurants")
    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

}
