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
import ru.testAssignment.voting.web.json.JsonUtil;


import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.testAssignment.voting.UserTestData.*;
import static ru.testAssignment.voting.VoteTestData.*;
import static ru.testAssignment.voting.util.ValidationUtil.TIME_LIMIT;

public class VoteRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = VoteController.REST_URL + '/';
    private static boolean timeBan = !LocalTime.now().isBefore(TIME_LIMIT);

    @Autowired
    private VoteService service;

    @Test
    public void testUpdate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote updated = getUpdatedVote();
        mockMvc.perform(put(REST_URL + VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(service.get(VOTE_ID, ADMIN_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        if (timeBan) ValidationUtil.setTest(true);                       // tests is affordable after 11:00
        Vote expected = getCreatedVote();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(ADMIN_ID), expected, VOTE6, VOTE7, VOTE8);
    }

    @Test(expected = NestedServletException.class)
    public void testCreateTimesUp() throws Exception {                  // test throw NestedServletException after 11:00

        if(timeBan) {
            Vote expected = getCreatedVote();
            ResultActions action = mockMvc.perform(post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(expected)))
                    .andExpect(status().isCreated());
        } else throw new NestedServletException("");

    }

    @Test
    public void testCreateChangeToUpdate() throws Exception{
        if (timeBan) ValidationUtil.setTest(true);                       // if the vote to the current date already exist, it will be updated
        Vote expected = getCreatedVote();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());


        service.getAll(ADMIN_ID).forEach(System.out::println);

        Vote newVote = new Vote(LocalDateTime.now(), RESTAURANT_ID + 4);

        ResultActions actionCreateToUpdate = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote newReturned = TestUtil.readFromJson(actionCreateToUpdate, Vote.class);
        newVote.setId(newReturned.getId());

        service.getAll(ADMIN_ID).forEach(System.out::println);
        assertMatch(newReturned, newVote);
        assertMatch(service.getAll(ADMIN_ID), newVote, VOTE6, VOTE7, VOTE8);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(service.getAll(ADMIN_ID), VOTE7, VOTE8);
    }
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE6));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE6, VOTE7, VOTE8)));
    }

    @Test
    public void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "date")
                .param("dateTime", "2015-05-30"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVote(VOTE1, VOTE3, VOTE6));
    }

    @Test
    public void testGetVotedUsers() throws Exception {
        mockMvc.perform(get(REST_URL + "voted")
                .param("dateTime", "2015-05-28"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER2, ADMIN));

    }
}