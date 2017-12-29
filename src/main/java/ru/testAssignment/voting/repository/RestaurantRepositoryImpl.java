package ru.testAssignment.voting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.testAssignment.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant, int userId) {
        if (!restaurant.isNew() && get(restaurant.getId(), userId) == null) return null;

        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRestaurantRepository.delete(id, userId) != 0;
    }

    @Override
    public Restaurant get(int id, int userId) {
        /*return crudRestaurantRepository.findById(id).filter(restourant -> restourant.getUser().getId() == userId).orElse(null);*/

        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurantsOfHistory(int userId) {
        return crudRestaurantRepository.getAll(userId);
    }

    @Override
    public List<Restaurant> getbyDate(LocalDate date, int userId) {
        return crudRestaurantRepository.getByDate(date, userId);
    }
}
