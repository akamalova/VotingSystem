package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.service.MenuService;
import ru.testAssignment.voting.web.Menu.MenuRestAdminController;
import ru.testAssignment.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.MenuTestData.*;
import static ru.testAssignment.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.testAssignment.voting.TestUtil.userHttpBasic;
import static ru.testAssignment.voting.UserTestData.ADMIN;

public class MenuRestAdminControllerTest extends AbstractControllerTest{

    private static final String REST_URL = MenuRestAdminController.REST_URL + '/';

    @Autowired
    private MenuService service;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MENU_ID, RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonMenuOb(MENU1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1, RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MENU_ID, RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(RESTAURANT_ID), MENU2);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1, RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {

        TestUtil.print(mockMvc.perform(get(REST_URL, RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonMenu(MENU1, MENU2)));
    }

    @Test
    public void testCreate() throws Exception {
        Menu expected = getCreatedMenu();
        ResultActions action = mockMvc.perform(post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());

        Menu returned = TestUtil.readFromJson(action, Menu.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(RESTAURANT_ID), MENU1, expected, MENU2);
    }

    @Test
    public void testUpdate() throws Exception {
        Menu updated = getUpdatedMenu();
        mockMvc.perform(put(REST_URL + MENU_ID, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        assertMatch(service.get(MENU_ID, RESTAURANT_ID), updated);
    }

    @Test
    public void testGetByDate() throws Exception {

        mockMvc.perform(get(REST_URL + "date", RESTAURANT_ID)
                .param("dateTime", "2017-05-30")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonMenu(MENU1));
    }

}