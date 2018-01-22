package ru.voting.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting.TestUtil;
import ru.voting.UserTestData;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.service.UserService;
import ru.voting.to.UserTo;
import ru.voting.web.User.UserRestAdminController;
import ru.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.util.UserUtil.asTo;
import static ru.voting.util.UserUtil.updateFromTo;


public class UserRestAdminControllerTest extends AbstractControllerTest{

    private static final String REST_URL = UserRestAdminController.REST_URL + '/';

    @Autowired
    protected UserService userService;

    @Before
    public void setUp() {
        cacheManager.getCache("users").clear();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(UserTestData.ADMIN, UserTestData.USER2, UserTestData.USER1)));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.ADMIN_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(UserTestData.ADMIN));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        UserTo expected = asTo(user);

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(expected, "newPass"))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        user.setId(returned.getId());
        User resultExpected = updateFromTo(user, expected);

        UserTestData.assertMatch(returned, resultExpected);
        UserTestData.assertMatch(userService.getAll(), UserTestData.ADMIN, UserTestData.USER2, resultExpected, UserTestData.USER1);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + UserTestData.USER_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        UserTestData.assertMatch(userService.getAll(), UserTestData.ADMIN, UserTestData.USER2);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User(UserTestData.USER1);
        user.setName("UpdatedName");

        UserTo updated = asTo(user);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk());

        User resultExpected = updateFromTo(user, updated);
        UserTestData.assertMatch(userService.get(UserTestData.USER_ID), resultExpected);
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + UserTestData.USER1.getEmail())
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(UserTestData.USER1));
    }

}