package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.service.VoteService;
import ru.testAssignment.voting.util.ValidationUtil;
import ru.testAssignment.voting.web.Vote.VoteRestProfileController;
import ru.testAssignment.voting.web.json.JsonUtil;

import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.UserTestData.*;
import static ru.testAssignment.voting.VoteTestData.*;
import static ru.testAssignment.voting.util.ValidationUtil.TIME_LIMIT;

public class VoteRestProfileControllerTest extends AbstractControllerTest{
    private static final String REST_URL = VoteRestProfileController.REST_URL + '/';
    private static boolean timeBan = !LocalTime.now().isBefore(TIME_LIMIT);

    @Autowired
    private VoteService service;

    @Test
    public void testUpdate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote updated = getUpdatedVote();
        updated.setId(VOTE_ID_CONTR);
        mockMvc.perform(put(REST_URL + VOTE_ID_CONTR)
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(service.get(VOTE_ID_CONTR, USER_ID), updated);
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote updated = getUpdatedVote();
        updated.setId(VOTE_ID_CONTR + 2);
        mockMvc.perform(put(REST_URL + (VOTE_ID_CONTR + 2))
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote expected = getCreatedVote();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getByUser(USER_ID), expected, VOTE1, VOTE2);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE_ID_CONTR)
                .with(TestUtil.userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getByUser(USER_ID), VOTE2);
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE_ID_CONTR)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isForbidden()));
    }
}