package ru.testAssignment.voting.repository.menu;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MenuRepositoryImpl implements MenuRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId, int userId) {
        User user = em.getReference( User.class, userId);
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            if (!menu.isNew() && get(menu.getId()) == null) return null;

            menu.setRestaurant(em.getReference(Restaurant.class, restaurantId));
            return em.merge(menu);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId, int userId) {
        User user = em.getReference( User.class, userId);
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            return em.createNamedQuery(Menu.DELETE)
                    .setParameter("id", id)
                    .setParameter("restaurantId", restaurantId)
                    .executeUpdate() != 0;
        } return false;
    }

    @Override
    public Menu get(int id) {
        return  em.find(Menu.class, id);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return em.createNamedQuery(Menu.ALL, Menu.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    @Override
    public List<Menu> getByDate(LocalDate dateTime) {
        return em.createNamedQuery(Menu.All_BY_DATE, Menu.class)
                .setParameter("dateTime", dateTime)
                .getResultList();
    }

    @Override
    public List<Menu> getRestaurantByDate(LocalDate dateTime, int restaurantId) {
        return em.createNamedQuery(Menu.All_BY_DATE, Menu.class)
                .setParameter("dateTime", dateTime)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    @Override
    public Menu getWithRestaurant(int id) {
        return null;
    }
}
