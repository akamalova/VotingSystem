package ru.voting.service;

import ru.voting.model.User;
import ru.voting.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VoteService {

    Vote update(Vote vote, int userId, LocalTime time);

    Vote create(Vote vote, int userId, LocalTime time);

    Vote get(int id, int userId);

    List<Vote> getAll();

    List<Vote> getByDate(LocalDate date);

    List<Vote> getByUser(int userId);

    List<User> getVoted(LocalDate date) ;
}
