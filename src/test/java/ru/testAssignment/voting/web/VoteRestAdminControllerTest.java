package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.service.VoteService;
import ru.testAssignment.voting.util.ValidationUtil;
import ru.testAssignment.voting.web.Vote.VoteRestAdminController;
import ru.testAssignment.voting.web.json.JsonUtil;


import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.testAssignment.voting.TestUtil.userHttpBasic;
import static ru.testAssignment.voting.UserTestData.*;
import static ru.testAssignment.voting.VoteTestData.*;
import static ru.testAssignment.voting.util.ValidationUtil.TIME_LIMIT;

public class VoteRestAdminControllerTest extends AbstractControllerTest{

    private static final String REST_URL = VoteRestAdminController.REST_URL + '/';
    private static boolean timeBan = !LocalTime.now().isBefore(TIME_LIMIT);

    @Autowired
    private VoteService service;

    @Test
    public void testUpdate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote updated = getUpdatedVote();
        mockMvc.perform(put(REST_URL + VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        assertMatch(service.get(VOTE_ID, ADMIN_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote expected = getCreatedVote();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getByUser(ADMIN_ID), expected, VOTE6, VOTE7, VOTE8);
    }

    @Test
    public void testCreateTimesUp() throws Exception {
        if(timeBan) {
            Vote expected = getCreatedVote();
            ResultActions action = mockMvc.perform(post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(expected))
                    .with(userHttpBasic(ADMIN)))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getByUser(ADMIN_ID), VOTE7, VOTE8);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE6));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE1, VOTE3, VOTE6, VOTE7, VOTE4, VOTE2, VOTE5, VOTE8)));
    }

    @Test
    public void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "date")
                .param("dateTime", "2015-05-30")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE1, VOTE3, VOTE6));
    }

    @Test
    public void testGetVotedUsers() throws Exception {
        mockMvc.perform(get(REST_URL + "voted")
                .param("dateTime", "2015-05-28")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER2, ADMIN));

    }

    @Test
    public void testGetByUser() throws Exception {
        mockMvc.perform(get(REST_URL + "byUser/" + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE1, VOTE2));
    }
}