package ru.testAssignment.voting.repository.restaurant;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.AuthorizedUser;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.model.Role;
import ru.testAssignment.voting.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant, int userId) {
        User user = em.getReference( User.class, userId);
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            if (!restaurant.isNew() && get(restaurant.getId()) == null) return null;
            if (restaurant.isNew()) {
                em.persist(restaurant);
                return restaurant;
            }
            return em.merge(restaurant);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.getReference( User.class, userId);
        if (user.getRoles().contains(Role.ROLE_ADMIN)){
            return em.createNamedQuery(Restaurant.DELETE)
                    .setParameter("id", id)
                    .executeUpdate() != 0;
        }
        else return false;
    }

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.ALL_SORTED, Restaurant.class).getResultList();
    }
}
