package ru.voting.web.json;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

import ru.voting.UserTestData;
import ru.voting.VoteTestData;
import ru.voting.model.User;
import ru.voting.model.Vote;

import java.util.List;

public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(VoteTestData.VOTE1);
        System.out.println(json);
        Vote vote = JsonUtil.readValue(json, Vote.class);
        VoteTestData.assertMatch(vote, VoteTestData.VOTE1);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(VoteTestData.VOTES);
        System.out.println(json);
        List<Vote> voteList = JsonUtil.readValues(json, Vote.class);
        VoteTestData.assertMatch(voteList, VoteTestData.VOTES);
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