package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.service.VoteService;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.VoteTestData.*;

public class VoteControllerTest extends AbstractControllerTest{

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    public void update() throws Exception {
    }

    @Test
    public void createWithLocation() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE2)));
    }

    @Test
    public void getbyDate() throws Exception {
    }

    @Test
    public void getNotVoted() throws Exception {
    }

}