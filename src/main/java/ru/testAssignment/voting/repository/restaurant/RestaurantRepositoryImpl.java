package ru.testAssignment.voting.repository.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.testAssignment.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant, int userId) {
        if (!restaurant.isNew() && get(restaurant.getId()) == null) return null;

        return restaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id, int userId) {
        return restaurantRepository.delete(id, userId) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Override
    public List<Restaurant> getbyDate(LocalDate date) {
        return restaurantRepository.getByDate(date);
    }
}
