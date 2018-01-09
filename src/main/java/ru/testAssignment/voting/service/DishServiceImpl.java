package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.repository.dish.DishRepository;
import ru.testAssignment.voting.util.exception.NotFoundException;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository dishRepository;

    @Override
    public Dish update(Dish dish, int menuId) throws NotFoundException {
        return dishRepository.save(dish, menuId);
    }

    @Override
    public Dish create(Dish dish, int menuId) {
        return dishRepository.save(dish, menuId);
    }

    @Override
    public void delete(int id, int menuId) throws NotFoundException {
        dishRepository.delete(id, menuId);
    }

    @Override
    public Dish get(int id, int menuId) throws NotFoundException {
        return dishRepository.get(id, menuId);
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return dishRepository.getAll(menuId);
    }

    @Override
    public Dish getWithMenu(int id) {
        return dishRepository.getWithMenu(id);
    }
}
