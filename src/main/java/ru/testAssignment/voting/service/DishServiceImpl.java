package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.repository.dish.DishRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.checkNotFound;
import static ru.testAssignment.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository dishRepository;

    @Override
    public Dish update(Dish dish, int menuId) throws NotFoundException {
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(dishRepository.save(dish, menuId), dish.getId());
    }

    @Override
    public Dish create(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish, menuId);
    }

    @Override
    public void delete(int id, int menuId) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(id, menuId), id);
    }

    @Override
    public Dish get(int id, int menuId) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.get(id, menuId), id);
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return checkNotFound(dishRepository.getAll(menuId), "menuId=" + menuId);
    }
}
