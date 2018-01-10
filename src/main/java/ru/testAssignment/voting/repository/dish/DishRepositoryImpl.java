package ru.testAssignment.voting.repository.dish;

import org.springframework.stereotype.Repository;
import ru.testAssignment.voting.model.Dish;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.model.Role;
import ru.testAssignment.voting.model.User;

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
    public Dish save(Dish dish, int menuId, int userId) {
        User user = em.getReference( User.class, userId);
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            if (!dish.isNew() && get(dish.getId(), menuId) == null) return null;

            dish.setMenu(em.getReference(Menu.class, menuId));
            return em.merge(dish);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int menuId, int userId) {
        User user = em.getReference(User.class, userId);
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            return em.createNamedQuery(Dish.DELETE)
                    .setParameter("id", id)
                    .setParameter("menuId", menuId)
                    .executeUpdate() != 0;
        }
        return false;
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
