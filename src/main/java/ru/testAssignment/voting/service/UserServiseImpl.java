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
    public User save(User restaurant, int userId) {
        return repository.save(restaurant, userId);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.delete(id, userId);
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    @Override
    public List<User> getNotVoted() {
        return repository.getNotVoted();
    }
}
