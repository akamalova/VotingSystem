package ru.voting.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer>{

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Override
    Restaurant save(Restaurant item);

    @Query("SELECT m FROM Restaurant m LEFT JOIN FETCH m.menu ORDER BY m.description desc")
    List<Restaurant> getAll();


}
