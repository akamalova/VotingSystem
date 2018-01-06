package ru.testAssignment.voting.repository.vote;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VoteRepositoryImpl implements VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Vote save(Vote vote, int userId) {

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
    public List<Vote> getByDate(LocalDate date, int userId) {
        return em.createNamedQuery(Vote.GET_BY_DATE, Vote.class)
                .setParameter("userId", userId)
                .setParameter("dateTime", date)
                .getResultList();
    }

    @Override
    public List<Vote> getByDate(LocalDate date) {
        return em.createNamedQuery(Vote.GET_BY_DATE, Vote.class)
                .setParameter("dateTime", date)
                .getResultList();
    }
}
