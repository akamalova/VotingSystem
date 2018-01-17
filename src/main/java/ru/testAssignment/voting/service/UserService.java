package ru.testAssignment.voting.service;


import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.to.UserTo;

import java.time.LocalDate;
import java.util.List;


public interface UserService {

    User create(User user);

    void update(User user);

    void update(UserTo userTo);

    void delete(int id);

    User get(int id);

    List<User> getAll();

    void enable(int id, boolean enable);

    User getByEmail(String email);

    List<User> getByDate(LocalDate date);
}
