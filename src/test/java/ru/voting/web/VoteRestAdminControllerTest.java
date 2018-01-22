package ru.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting.TestUtil;
import ru.voting.UserTestData;
import ru.voting.VoteTestData;
import ru.voting.model.Vote;
import ru.voting.service.VoteService;
import ru.voting.util.ValidationUtil;
import ru.voting.web.Vote.VoteRestAdminController;
import ru.voting.web.json.JsonUtil;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.util.ValidationUtil.TIME_LIMIT;

public class VoteRestAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestAdminController.REST_URL + '/';
    private static boolean timeBan = !LocalTime.now().isBefore(TIME_LIMIT);

    @Autowired
    private VoteService service;

    @Test
    public void testUpdate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote updated = VoteTestData.getUpdatedVote();
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + VoteTestData.VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk());

        VoteTestData.assertMatch(service.get(VoteTestData.VOTE_ID, UserTestData.ADMIN_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote expected = VoteTestData.getCreatedVote();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        VoteTestData.assertMatch(returned, expected);
        VoteTestData.assertMatch(service.getByUser(UserTestData.ADMIN_ID), expected, VoteTestData.VOTE6, VoteTestData.VOTE7, VoteTestData.VOTE8);
    }

    @Test
    public void testCreateTimesUp() throws Exception {
        if (timeBan) {
            Vote expected = VoteTestData.getCreatedVote();
            ResultActions action = mockMvc.perform(post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(expected))
                    .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + VoteTestData.VOTE_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VoteTestData.assertMatch(service.getByUser(UserTestData.ADMIN_ID), VoteTestData.VOTE7, VoteTestData.VOTE8);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VoteTestData.VOTE_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.contentJsonVote(VoteTestData.VOTE6));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.contentJsonVote(VoteTestData.VOTE1, VoteTestData.VOTE3, VoteTestData.VOTE6, VoteTestData.VOTE7, VoteTestData.VOTE4, VoteTestData.VOTE2, VoteTestData.VOTE5, VoteTestData.VOTE8)));
    }

    @Test
    public void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "date")
                .param("dateTime", "2015-05-30")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.contentJsonVote(VoteTestData.VOTE1, VoteTestData.VOTE3, VoteTestData.VOTE6));
    }

    @Test
    public void testGetVotedUsers() throws Exception {
        mockMvc.perform(get(REST_URL + "voted")
                .param("dateTime", "2015-05-28")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(UserTestData.USER2, UserTestData.ADMIN));

    }

    @Test
    public void testGetByUser() throws Exception {
        mockMvc.perform(get(REST_URL + "byUser/" + UserTestData.USER_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.contentJsonVote(VoteTestData.VOTE1, VoteTestData.VOTE2));
    }
}