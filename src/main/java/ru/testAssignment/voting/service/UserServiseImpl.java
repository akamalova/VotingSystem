package ru.testAssignment.voting.service;

import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.repository.UserRepository;

import java.util.List;

public class UserServiseImpl implements UserService{

    private UserRepository repository;

    @Override
    public List<User> getNotVoted() {
        return repository.getNotVoted();
    }
}
