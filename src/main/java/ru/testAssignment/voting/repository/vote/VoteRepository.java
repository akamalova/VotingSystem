package ru.testAssignment.voting.repository.vote;

import ru.testAssignment.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId);

    Vote get(int id, int userId);

    List<Vote> getAll(int userId);

    List<Vote> getByDate(LocalDate date, int userId);

    List<Vote> getByDate(LocalDate date);

}
