package ru.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.voting.UserTestData;
import ru.voting.model.User;
import ru.voting.service.UserService;
import ru.voting.to.UserTo;
import ru.voting.web.User.UserRestProfileController;
import ru.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.TestUtil.userHttpBasic;
import static ru.voting.util.UserUtil.asTo;
import static ru.voting.util.UserUtil.updateFromTo;

public class UserRestProfileControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserRestProfileController.REST_URL + '/';

    @Autowired
    protected UserService userService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(UserTestData.USER1));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(UserTestData.USER1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        UserTestData.assertMatch(userService.getAll(), UserTestData.ADMIN, UserTestData.USER2);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User(UserTestData.USER1);
        user.setName("UpdatedName");

        UserTo updated = asTo(user);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.USER1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        User resultExpected = updateFromTo(user, updated);
        UserTestData.assertMatch(userService.get(UserTestData.USER_ID), resultExpected);
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + UserTestData.USER2.getEmail())
                .with(userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isNotFound());
    }
}