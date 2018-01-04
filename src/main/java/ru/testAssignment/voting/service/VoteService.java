package ru.testAssignment.voting.service;

import ru.testAssignment.voting.model.Vote;

import java.util.List;

public interface VoteService {

    Vote update(Vote vote);

    Vote save(Vote vote);

    Vote get(int id, int userId);

    List<Vote> getAll(int userId);
}
