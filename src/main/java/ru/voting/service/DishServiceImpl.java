package ru.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.voting.model.Dish;
import ru.voting.repository.dish.DishRepository;
import ru.voting.util.exception.NotFoundException;

import java.util.List;

import static ru.voting.util.ValidationUtil.checkNotFound;
import static ru.voting.util.ValidationUtil.checkNotFoundWithId;

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
