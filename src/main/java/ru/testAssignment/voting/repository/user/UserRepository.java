package ru.testAssignment.voting.repository.user;

import ru.testAssignment.voting.model.User;

import java.util.List;

public interface UserRepository {

    User save(User restaurant, int userId);

    boolean delete(int id, int userId);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    List<User> getNotVoted() ;
}
