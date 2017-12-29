package ru.testAssignment.voting.repository;

import ru.testAssignment.voting.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getNotVoted();
}
