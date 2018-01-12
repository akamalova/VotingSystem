package ru.testAssignment.voting.web.json;

import org.junit.Test;

import static ru.testAssignment.voting.VoteTestData.*;
import ru.testAssignment.voting.model.Vote;

import java.util.List;

public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(VOTE1);
        System.out.println(json);
        Vote vote = JsonUtil.readValue(json, Vote.class);
        assertMatch(vote, VOTE1);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(VOTES);
        System.out.println(json);
        List<Vote> voteList = JsonUtil.readValues(json, Vote.class);
        assertMatch(voteList, VOTES);
    }
}