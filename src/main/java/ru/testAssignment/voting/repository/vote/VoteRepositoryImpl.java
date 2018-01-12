package ru.testAssignment.voting.repository.vote;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.util.exception.TimesUpException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional(readOnly = true)
public class VoteRepositoryImpl implements VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Vote save(Vote vote, int userId, LocalTime time) {

        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(em.getReference(User.class, userId));
        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else {
            return em.merge(vote);
        }
    }

    @Override
    public Vote get(int id, int userId) {
        Vote vote = em.find(Vote.class, id);
        return vote != null && vote.getUser().getId() == userId ? vote : null;
    }

    @Override
    public List<Vote> getAll(int userId) {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean delete (int id, int userId) {
        return em.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public List<Vote> getByDate(LocalDate date) {
        return em.createNamedQuery(Vote.GET_VOTES_BY_DATE, Vote.class)
                .setParameter("dateTimeMin", LocalDateTime.of(date, LocalTime.MIN))
                .setParameter("dateTimeMax", LocalDateTime.of(date, LocalTime.MAX))
                .getResultList();
    }

    @Override
    public List<User> getVotedUsers(LocalDate date) {

        return getByDate(date).stream()
                .map(Vote::getUser)
                .collect(toList());
    }
}
