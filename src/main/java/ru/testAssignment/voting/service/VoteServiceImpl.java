package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.repository.vote.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.checkNotFound;
import static ru.testAssignment.voting.util.ValidationUtil.checkNotFoundWithId;
import static ru.testAssignment.voting.util.ValidationUtil.checkTime;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository repository;

    @Override
    public Vote update(Vote vote, int userId, LocalTime time) {
        checkTime(time);
        return checkNotFoundWithId(repository.save(vote, userId, time), vote.getId());
    }

    @Override
    public Vote create(Vote vote, int userId, LocalTime time) {
        checkTime(time);
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote, userId, time);
    }

    @Override
    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Vote> getByDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(repository.getByDate(date), "date=" + date);
    }

    @Override
    public List<User> getVoted(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(repository.getVotedUsers(date), "date=" + date);
    }
}
