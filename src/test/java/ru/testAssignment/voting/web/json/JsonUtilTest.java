package ru.testAssignment.voting.web.json;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static ru.testAssignment.voting.VoteTestData.*;
import ru.testAssignment.voting.UserTestData;
import ru.testAssignment.voting.model.User;
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

    @Test
    public void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER1);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER1, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        Assert.assertEquals(user.getPassword(), "newPass");
    }
}