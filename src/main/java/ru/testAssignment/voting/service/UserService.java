package ru.testAssignment.voting.service;

import org.springframework.stereotype.Service;
import ru.testAssignment.voting.model.User;

import java.util.List;

@Service
public interface UserService {

    List<User> getNotVoted();
}
