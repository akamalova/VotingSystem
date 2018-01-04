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

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id, int userId);

    @Override
    Restaurant save(Restaurant item);

    @Query("SELECT m FROM Restaurant m ORDER BY m.dateTime DESC")
    List<Restaurant> getAll();

    @Query("SELECT m FROM Restaurant m WHERE m.dateTime=:date")
    List<Restaurant> getByDate(LocalDate date);

}
