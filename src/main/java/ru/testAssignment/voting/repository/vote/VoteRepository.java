package ru.testAssignment.voting.repository.vote;

import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId, LocalTime time);

    Vote get(int id, int userId);

    List<Vote> getAll(int userId);

    boolean delete(int id, int userId);

    List<Vote> getByDate(LocalDate date);

    List<User> getVotedUsers(LocalDate date) ;
}
