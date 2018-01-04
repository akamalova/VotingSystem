package ru.testAssignment.voting.repository.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.testAssignment.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Autowired(required = true)
    private CrudRestaurantRepository crudRestaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant, int userId) {
        if (!restaurant.isNew() && get(restaurant.getId()) == null) return null;

        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRestaurantRepository.delete(id, userId) != 0;
    }

    @Override
    public Restaurant get(int id) {
        /*return crudRestaurantRepository.findById(id).filter(restourant -> restourant.getUser().getId() == userId).orElse(null);*/

        return null;
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.getAll();
    }

    @Override
    public List<Restaurant> getbyDate(LocalDate date) {
        return crudRestaurantRepository.getByDate(date);
    }
}
