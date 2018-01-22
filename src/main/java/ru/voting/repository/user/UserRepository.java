package ru.voting.repository.user;

import ru.voting.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);
}
