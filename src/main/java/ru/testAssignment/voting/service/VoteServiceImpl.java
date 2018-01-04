package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.repository.vote.VoteRepository;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository repository;

    @Override
    public Vote update(Vote vote) {
        return repository.save(vote);
    }

    @Override
    public Vote save(Vote vote) {
        return repository.save(vote);
    }

    @Override
    public Vote get(int id, int userId) {
        return repository.get(id, userId);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }
}
