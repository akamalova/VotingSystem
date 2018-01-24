package ru.voting.service;


import ru.voting.model.User;
import ru.voting.to.UserTo;
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
}
