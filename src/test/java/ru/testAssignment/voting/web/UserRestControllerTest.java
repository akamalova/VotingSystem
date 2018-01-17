package ru.testAssignment.voting.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.model.Role;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.service.UserService;
import ru.testAssignment.voting.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.UserTestData.*;

public class UserRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = UserController.REST_URL + '/';

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
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN, USER2, USER1)));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, USER2, expected, USER1);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER2);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1));
    }

    @Test
    public void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "date")
                .param("dateTime", "2014-05-30"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER2, ADMIN));
    }

}