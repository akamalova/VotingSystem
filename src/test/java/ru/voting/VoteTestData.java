package ru.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting.model.Vote;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.voting.web.json.JsonUtil.writeIgnoreProps;

public class VoteTestData {
    public static final int VOTE_ID_TEST = START_SEQ + 29;
    public static final int VOTE_ID = START_SEQ + 24;

    public static final Vote VOTE1 = new Vote(VOTE_ID, RestaurantTestData.RESTAURANT_ID);
    public static final Vote VOTE2 = new Vote(VOTE_ID + 1, RestaurantTestData.RESTAURANT_ID + 2);
    public static final Vote VOTE3 = new Vote(VOTE_ID + 2, RestaurantTestData.RESTAURANT_ID);
    public static final Vote VOTE4 = new Vote(VOTE_ID + 3, RestaurantTestData.RESTAURANT_ID + 1);
    public static final Vote VOTE5 = new Vote(VOTE_ID + 4, RestaurantTestData.RESTAURANT_ID + 4);
    public static final Vote VOTE6 = new Vote(VOTE_ID + 5, RestaurantTestData.RESTAURANT_ID + 1);
    public static final Vote VOTE7 = new Vote(VOTE_ID + 6, RestaurantTestData.RESTAURANT_ID);
    public static final Vote VOTE8 = new Vote(VOTE_ID + 7,  RestaurantTestData.RESTAURANT_ID + 4);

    public static final List<Vote> VOTES = Arrays.asList(VOTE6, VOTE5, VOTE4, VOTE3, VOTE2, VOTE1);

    public static Vote getCreatedVote() {
        return new Vote(null, RestaurantTestData.RESTAURANT_ID);
    }

    public static Vote getUpdatedVote() {
        return new Vote(VOTE_ID_TEST,RestaurantTestData.RESTAURANT_ID + 1);
    }


    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "date");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "date").isEqualTo(expected);
    }

    public static ResultMatcher contentJsonVote(Vote... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "user", "date"));
    }

    public static ResultMatcher contentJsonVote(Vote expected) {
        return content().json(writeIgnoreProps(expected, "user", "date"));
    }
}
