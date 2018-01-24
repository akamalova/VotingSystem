package ru.voting.repository.vote;

import ru.voting.model.User;
import ru.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId);

    Vote get(int id, int userId);

    List<Vote> getAll();

    List<Vote> getByDate(LocalDate date);

    List<Vote> getByUser(int userId);

    List<User> getVotedUsers(LocalDate date);
}
