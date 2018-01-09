package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.repository.menu.MenuRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository repository;

    @Override
    public Menu update(Menu menu, int restaurantId) throws NotFoundException {
        return repository.save(menu, restaurantId);
    }

    @Override
    public Menu create(Menu menu, int restaurantId) {
        return repository.save(menu, restaurantId);
    }

    @Override
    public void delete(int id, int restaurantId) throws NotFoundException {
        repository.delete(id, restaurantId);
    }

    @Override
    public Menu get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    @Override
    public List<Menu> getByDate(LocalDate date) {
        return repository.getByDate(date);
    }

    @Override
    public List<Menu> getRestaurantByDate(LocalDate date, int restaurantId) {
        return repository.getRestaurantByDate(date, restaurantId);
    }

    @Override
    public Menu getWithRestaurant(int id) {
        return repository.getWithRestaurant(id);
    }
}
