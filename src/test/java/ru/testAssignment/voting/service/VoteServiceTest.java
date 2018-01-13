package ru.testAssignment.voting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.util.exception.NotFoundException;
import ru.testAssignment.voting.util.exception.TimesUpException;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.testAssignment.voting.UserTestData.*;
import static ru.testAssignment.voting.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest{

    @Autowired
    private VoteService service;

    @Test
    public void update() throws Exception {
        Vote updated = getUpdatedVote();
        service.update(updated, ADMIN_ID, LocalTime.of(10,5,15));
        assertMatch(service.get(VOTE_ID, ADMIN_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + VOTE_ID);
        service.update(VOTE6, USER_ID, LocalTime.of(10,5,15));
    }

    @Test
    public void updateTimesUp() throws Exception {
        thrown.expect(TimesUpException.class);
        thrown.expectMessage("You are already voted today!");
        service.update(VOTE1, ADMIN_ID, LocalTime.of(11,0,0));
    }

    @Test
    public void create() throws Exception {
        Vote created = getCreatedVote();
        service.create(created, USER_ID, LocalTime.of(10,5,15));
        assertMatch(service.getAll(USER_ID), created, VOTE1, VOTE2);
    }

    @Test
    public void createTimesUp() throws Exception {
        thrown.expect(TimesUpException.class);
        thrown.expectMessage("You are already voted today!");
        Vote created = getCreatedVote();
        service.create(created, USER_ID, LocalTime.of(11,5,15));
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(VOTE_ID, USER_ID);
    }

    @Test
    public void get() throws Exception {
        Vote actual = service.get(VOTE_ID, ADMIN_ID);
        assertMatch(actual, VOTE6);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), VOTE1, VOTE2);
    }

    @Test
    public void getByDate() throws Exception {
        assertMatch(service.getByDate(LocalDate.of(2015,5,30)), VOTE1, VOTE3, VOTE6);
    }

    @Test
    public void delete() throws Exception {
        service.delete(VOTE_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), VOTE7, VOTE8);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(VOTE_ID, 1);
    }

    @Test
    public void getVotedUsers() throws Exception {
        assertMatch(service.getVoted(LocalDate.of(2015,5,28)), USER2, ADMIN);
    }

}