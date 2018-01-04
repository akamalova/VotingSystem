package ru.testAssignment.voting.repository.user;

import ru.testAssignment.voting.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getNotVoted();
}
