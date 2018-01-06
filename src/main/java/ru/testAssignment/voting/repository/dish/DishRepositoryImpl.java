package ru.testAssignment.voting.repository.dish;

import org.springframework.stereotype.Repository;
import ru.testAssignment.voting.model.Dish;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.model.Menu;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DishRepositoryImpl implements DishRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Dish save(Dish dish, int menuId) {
        if (!dish.isNew() && get(dish.getId(), menuId) == null) return null;

        dish.setMenu(em.getReference(Menu.class, menuId));
        return em.merge(dish);
    }

    @Override
    @Transactional
    public boolean delete(int id, int menuId) {
        return em.createNamedQuery(Dish.DELETE)
                .setParameter("id", id)
                .setParameter("menuId", menuId)
                .executeUpdate() != 0;
    }

    @Override
    public Dish get(int id, int menuId) {
        Dish dish = em.find(Dish.class, id);
        return  dish!= null && dish.getMenu().getId() == menuId? dish : null;
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return em.createNamedQuery(Dish.ALL, Dish.class)
                .setParameter("menuId", menuId)
                .getResultList();
    }

    @Override
    public Dish getWithMenu(int id) {
        return null;
    }
}
