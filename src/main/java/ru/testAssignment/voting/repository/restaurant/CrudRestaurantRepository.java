package ru.testAssignment.voting.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id")int id,@Param("userId") int userId);

    @Override
    Restaurant save(Restaurant item);

    @Query("SELECT m FROM Restaurant m")
    List<Restaurant> getAll();

    @Query("SELECT m FROM Restaurant m WHERE m.dateTime = ?1")
    List<Restaurant> getByDate(LocalDate date);

}
