package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.repository.menu.MenuRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.checkNotFound;
import static ru.testAssignment.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository repository;

    @Override
    public Menu update(Menu menu, int restaurantId, int userId) throws NotFoundException {
        Assert.notNull(menu, "menu must not be null");
        return checkNotFoundWithId(repository.save(menu, restaurantId, userId), menu.getId());
    }

    @Override
    public Menu create(Menu menu, int restaurantId, int userId) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu, restaurantId, userId);
    }

    @Override
    public void delete(int id, int restaurantId, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, restaurantId, userId), id);
    }

    @Override
    public Menu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    @Override
    public List<Menu> getByDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(repository.getByDate(date), "date=" + date);
    }

    @Override
    public Menu getWithRestaurant(int id) {
        return checkNotFoundWithId(repository.getWithRestaurant(id), id);
    }
}
