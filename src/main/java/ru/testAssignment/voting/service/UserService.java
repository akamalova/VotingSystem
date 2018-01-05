package ru.testAssignment.voting.service;


import ru.testAssignment.voting.model.User;

import java.util.List;


public interface UserService {

    List<User> getNotVoted();
}
