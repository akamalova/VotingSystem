package ru.testAssignment.voting.repository.user;

import ru.testAssignment.voting.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    List<User> getNotVoted() ;
}
