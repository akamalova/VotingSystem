package ru.testAssignment.voting.repository.vote;

import ru.testAssignment.voting.model.Vote;

import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote);

    // null if meal do not belong to userId
    Vote get(int id, int userId);

    // ORDERED dateTime desc
    List<Vote> getAll(int userId);
}
