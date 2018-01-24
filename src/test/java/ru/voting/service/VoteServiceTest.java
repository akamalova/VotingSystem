package ru.voting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.model.Vote;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimesUpException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.voting.UserTestData.*;
import static ru.voting.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void update() throws Exception {
        Vote updated = getUpdatedVote();
        service.update(updated, ADMIN_ID, LocalTime.of(10, 5, 15));
        assertMatch(service.get(VOTE_ID_TEST, ADMIN_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + VOTE_ID_TEST);
        service.update(VOTE6, USER_ID, LocalTime.of(10, 5, 15));
    }

    @Test
    public void updateTimesUp() throws Exception {
        thrown.expect(TimesUpException.class);
        thrown.expectMessage("Late to voted today!");
        service.update(VOTE1, ADMIN_ID, LocalTime.of(11, 0, 0));
    }

    @Test
    public void create() throws Exception {
        Vote created = getCreatedVote();
        service.create(created, USER_ID, LocalTime.of(11, 5, 15));
        List<Vote> list = service.getByUser(USER_ID);
        assertMatch(service.getByUser(USER_ID), VOTE1, VOTE2, created);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(VOTE_ID_TEST, USER_ID);
    }

    @Test
    public void get() throws Exception {
        Vote actual = service.get(VOTE_ID_TEST, ADMIN_ID);
        assertMatch(actual, VOTE6);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(), VOTE1, VOTE3, VOTE6, VOTE7, VOTE4, VOTE2, VOTE5, VOTE8);
    }

    @Test
    public void getByDate() throws Exception {

         assertMatch(service.getByDate(LocalDate.of(2015, 5, 30)), VOTE1, VOTE3, VOTE6);
    }

    @Test
    public void getVotedUsers() throws Exception {
        assertMatch(service.getVoted(LocalDate.of(2015, 5, 28)), USER2, ADMIN);
    }

    @Test
    public void getByUsers() throws Exception {
        assertMatch(service.getByUser(ADMIN_ID), VOTE6, VOTE7, VOTE8);
    }

}
