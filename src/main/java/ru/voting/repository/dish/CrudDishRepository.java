package ru.voting.repository.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer>{

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish m WHERE m.id=:id AND m.menu.id=:menuId")
    int delete(@Param("id") int id, @Param("menuId") int menuId);

    @Override
    @Transactional
    Dish save(Dish item);

    @Query("SELECT m FROM Dish m WHERE m.menu.id=:menuId")
    List<Dish> getAll(@Param("menuId") int menuId);

}
