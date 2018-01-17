package ru.testAssignment.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.repository.user.UserRepository;
import ru.testAssignment.voting.to.UserTo;

import java.time.LocalDate;
import java.util.List;

import static ru.testAssignment.voting.util.ToUtil.UserUtil.updateFromTo;
import static ru.testAssignment.voting.util.ValidationUtil.checkNotFound;
import static ru.testAssignment.voting.util.ValidationUtil.checkNotFoundWithId;


@Service
public class UserServiseImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(UserTo userTo) {

        User user = get(userTo.getId());
        repository.save(updateFromTo(user, userTo));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getByDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(repository.getByDate(date), "date=" + date);
    }
}
