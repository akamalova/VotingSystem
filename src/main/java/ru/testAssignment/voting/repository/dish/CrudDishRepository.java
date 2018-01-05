package ru.testAssignment.voting.repository.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.model.Dish;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId")int restaurantId);

    @Override
    Dish save(Dish item);

    @Query("SELECT m FROM Dish m WHERE m.restaurant.id=:restaurantId")
    List<Dish> getAll(Integer restaurantId);

    @Query("SELECT m FROM Dish m JOIN FETCH m.restaurant WHERE m.id = ?1")
    Dish getWithRestaurant(int id);
}
