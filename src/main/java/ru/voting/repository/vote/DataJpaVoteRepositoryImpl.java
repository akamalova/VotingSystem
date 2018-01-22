package ru.voting.repository.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.User;
import ru.voting.model.Vote;
import ru.voting.repository.user.CrudUserRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class DataJpaVoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    @Transactional
    public Vote save(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        Object j = crudUserRepository.getOne(userId);

        vote.setUser(crudUserRepository.getOne(userId));

        return crudVoteRepository.save(vote);
    }

    @Override
    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id).filter(vote -> vote.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.getAll();
    }

    @Override
    public List<Vote> getByUser(int userId) {
        return crudVoteRepository.getByUser(userId);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    @Override
    public List<Vote> getByDate(LocalDate date) {
        return crudVoteRepository.getByDate(date);
    }

    @Override
    public List<User> getVotedUsers(LocalDate date) {
        return getByDate(date).stream()
                .map(Vote::getUser)
                .collect(toList());
    }
}
