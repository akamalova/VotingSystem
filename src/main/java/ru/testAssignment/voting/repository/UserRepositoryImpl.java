package ru.testAssignment.voting.repository;

import org.springframework.stereotype.Repository;
import ru.testAssignment.voting.model.User;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> getNotVoted() {
        return null;
    }
}
