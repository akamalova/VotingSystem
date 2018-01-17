package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.service.DishService;
import ru.testAssignment.voting.web.Dish.DishRestAdminController;
import ru.testAssignment.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.DishTestData.*;
import static ru.testAssignment.voting.MenuTestData.MENU_ID;
import static ru.testAssignment.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.testAssignment.voting.TestUtil.userHttpBasic;
import static ru.testAssignment.voting.UserTestData.ADMIN;

public class DishRestAdminControllerTest extends AbstractControllerTest{

    @Autowired
    private DishService service;

    private static final String REST_URL = DishRestAdminController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonDish(DISH1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(MENU_ID), DISH2);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL, RESTAURANT_ID, MENU_ID)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonDish(DISH1, DISH2)));
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = getUpdatedDish();
        mockMvc.perform(put(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        assertMatch(service.get(DISH_ID, MENU_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Dish expected = getCreatedDish();
        ResultActions action = mockMvc.perform(post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(MENU_ID), DISH1, DISH2, expected);
    }

}