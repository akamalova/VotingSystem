package ru.testAssignment.voting.repository.vote;

import org.springframework.stereotype.Repository;
import ru.testAssignment.voting.model.Vote;

import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository {
    @Override
    public Vote save(Vote vote) {
        return null;
    }

    @Override
    public Vote get(int id, int userId) {
        return null;
    }

    @Override
    public List<Vote> getAll(int userId) {
        return null;
    }
}
