package ru.voting.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer>{

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Override
    @Transactional
    Menu save(Menu item);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes WHERE m.restaurant.id=:restaurantId ORDER BY m.date desc")
    List<Menu> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.date=:date")
    List<Menu> getByDate(@Param("date")LocalDate date);
}
