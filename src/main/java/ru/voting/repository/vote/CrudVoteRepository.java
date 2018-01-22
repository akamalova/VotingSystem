package ru.voting.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Vote;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer>{

    @Override
    @Transactional
    Vote save(Vote item);

    @Query("SELECT m FROM Vote m ORDER BY m.date DESC, m.restaurantId")
    List<Vote> getAll();

    @Query("SELECT m FROM Vote m LEFT JOIN FETCH m.user WHERE m.date=:date")
    List<Vote> getByDate(@Param("date")LocalDate date);

    @Query("SELECT m FROM Vote m WHERE m.user.id=:userId")
    List<Vote> getByUser(@Param("userId")int userId);
}
