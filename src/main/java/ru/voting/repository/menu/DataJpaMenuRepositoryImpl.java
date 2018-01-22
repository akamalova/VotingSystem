package ru.voting.repository.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Menu;
import ru.voting.repository.restaurant.CrudRestaurantRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class DataJpaMenuRepositoryImpl implements MenuRepository {

    @Autowired
    private CrudMenuRepository crudMenuRepository;

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        if (!menu.isNew() && get(menu.getId(), restaurantId) == null) return null;
        menu.setRestaurant(crudRestaurantRepository.getOne(restaurantId));

        return crudMenuRepository.save(menu);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudMenuRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Menu get(int id, int restaurantId) {
        return crudMenuRepository.findById(id).filter(menu -> menu.getRestaurant().getId() == restaurantId).orElse(null);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return crudMenuRepository.getAll(restaurantId);
    }

    @Override
    public List<Menu> getByDate(LocalDate date) {

        return crudMenuRepository.getByDate(date);
    }

}
