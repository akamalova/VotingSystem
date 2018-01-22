package ru.voting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.UserTestData;
import ru.voting.model.Vote;
import ru.voting.util.exception.NotFoundException;
import ru.voting.util.exception.TimesUpException;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ru.voting.VoteTestData.*;
import static ru.voting.util.DateTimeUtil.toDate;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void update() throws Exception {
        Vote updated = getUpdatedVote();
        service.update(updated, UserTestData.ADMIN_ID, LocalTime.of(10, 5, 15));
        assertMatch(service.get(VOTE_ID_TEST, UserTestData.ADMIN_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + VOTE_ID_TEST);
        service.update(VOTE6, UserTestData.USER_ID, LocalTime.of(10, 5, 15));
    }

    @Test
    public void updateTimesUp() throws Exception {
        thrown.expect(TimesUpException.class);
        thrown.expectMessage("Late to voted today!");
        service.update(VOTE1, UserTestData.ADMIN_ID, LocalTime.of(11, 0, 0));
    }

    @Test
    public void create() throws Exception {
        Vote created = getCreatedVote();
        service.create(created, UserTestData.USER_ID, LocalTime.of(10, 5, 15));
        List<Vote> list = service.getByUser(UserTestData.USER_ID);
        assertMatch(service.getByUser(UserTestData.USER_ID), VOTE1, VOTE2, created);
    }

    @Test
    public void createTimesUp() throws Exception {
        thrown.expect(TimesUpException.class);
        thrown.expectMessage("Late to voted today!");
        Vote created = getCreatedVote();
        service.create(created, UserTestData.USER_ID, LocalTime.of(11, 5, 15));
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(VOTE_ID_TEST, UserTestData.USER_ID);
    }

    @Test
    public void get() throws Exception {
        Vote actual = service.get(VOTE_ID_TEST, UserTestData.ADMIN_ID);
        assertMatch(actual, VOTE6);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(), VOTE1, VOTE3, VOTE6, VOTE7, VOTE4, VOTE2, VOTE5, VOTE8);
    }

    @Test
    public void getByDate() throws Exception {

       // assertMatch(, VOTE1, VOTE3, VOTE6);
    }

    @Test
    public void delete() throws Exception {
        service.delete(VOTE_ID_TEST, UserTestData.ADMIN_ID);
        assertMatch(service.getByUser(UserTestData.ADMIN_ID), VOTE7, VOTE8);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(VOTE_ID_TEST, 1);
    }

    @Test
    public void getVotedUsers() throws Exception {
        //UserTestData.assertMatch(service.getVoted(new Date(2015, 5, 28)), UserTestData.ADMIN, UserTestData.USER2);
    }

    @Test
    public void getByUsers() throws Exception {
        assertMatch(service.getByUser(UserTestData.ADMIN_ID), VOTE6, VOTE7, VOTE8);
    }

}