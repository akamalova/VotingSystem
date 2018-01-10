package ru.testAssignment.voting.service;

import ru.testAssignment.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    Vote update(Vote vote, int userId);

    Vote create(Vote vote, int userId);

    Vote get(int id, int userId);

    List<Vote> getAll(int userId);

    List<Vote> getByDate(LocalDate date, int userId);

    List<Vote> getByDate(LocalDate date);
}
