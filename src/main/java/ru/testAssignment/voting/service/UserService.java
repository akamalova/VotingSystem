package ru.testAssignment.voting.service;


import ru.testAssignment.voting.model.User;

import java.util.List;


public interface UserService {

    User save(User restaurant, int userId);

    boolean delete(int id, int userId);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    List<User> getNotVoted() ;
}
