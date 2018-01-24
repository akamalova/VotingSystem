package ru.voting.repository.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;

import java.util.List;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    @Autowired
    private CrudDishRepository crudDishRepository;

    @Override
    @Transactional
    public Dish save(Dish dish, int menuId) {
        if (!dish.isNew() && get(dish.getId(), menuId) == null) return null;

        if (dish.isNew()) dish.setMenuId(menuId);
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int menuId) {
        return crudDishRepository.delete(id, menuId) != 0;
    }

    @Override
    public Dish get(int id, int menuId) {
        return crudDishRepository.findById(id).filter(dish -> dish.getMenuId() == menuId).orElse(null);
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return crudDishRepository.getAll(menuId);
    }
}
