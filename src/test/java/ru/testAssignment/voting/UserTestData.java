package ru.testAssignment.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.testAssignment.voting.model.Role;
import ru.testAssignment.voting.model.User;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.testAssignment.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.testAssignment.voting.web.json.JsonUtil.writeIgnoreProps;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 2;


    public static final User USER1 = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User USER2 = new User(USER_ID + 1, "AnUser", "anuser@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "password", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "votes");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered"));
    }

}
