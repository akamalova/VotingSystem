package ru.voting.repository.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;
import ru.voting.repository.menu.CrudMenuRepository;

import java.util.List;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    @Autowired
    private CrudDishRepository crudDishRepository;

    @Autowired
    private CrudMenuRepository crudMenuRepository;

    @Override
    @Transactional
    public Dish save(Dish dish, int menuId) {
        if (!dish.isNew() && get(dish.getId(), menuId) == null)
            return null;

        dish.setMenu(crudMenuRepository.getOne(menuId));

        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int menuId) {
        return crudDishRepository.delete(id, menuId) != 0;
    }

    @Override
    public Dish get(int id, int menuId) {

        return crudDishRepository.findById(id).filter(dish -> dish.getMenu().getId() == menuId).orElse(null);
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return crudDishRepository.getAll(menuId);
    }
}
