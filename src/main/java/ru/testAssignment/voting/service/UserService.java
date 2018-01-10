package ru.testAssignment.voting.service;


import ru.testAssignment.voting.model.User;

import java.util.List;


public interface UserService {

    User create(User user);

    void update(User user);

    void delete(int id);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    List<User> getNotVoted() ;
}
