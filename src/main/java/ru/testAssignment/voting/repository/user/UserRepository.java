package ru.testAssignment.voting.repository.user;

import ru.testAssignment.voting.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    User get(int id);

    List<User> getAll();

    List<User> getByDate(LocalDate date);

    User getByEmail(String email);

}
