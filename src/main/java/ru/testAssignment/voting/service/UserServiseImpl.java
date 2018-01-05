package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.repository.user.UserRepository;

import java.util.List;


@Service
public class UserServiseImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getNotVoted() {
        return repository.getNotVoted();
    }
}
