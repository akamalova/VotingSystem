package ru.voting.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting.TestUtil;
import ru.voting.DishTestData;
import ru.voting.RestaurantTestData;
import ru.voting.UserTestData;
import ru.voting.model.Dish;
import ru.voting.service.DishService;
import ru.voting.web.Dish.DishRestAdminController;
import ru.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.DishTestData.assertMatch;
import static ru.voting.MenuTestData.MENU_ID;

public class DishRestAdminControllerTest extends AbstractControllerTest {

    @Autowired
    private DishService service;

    private static final String REST_URL = DishRestAdminController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DishTestData.DISH_ID, RestaurantTestData.RESTAURANT_ID, MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJsonDish(DishTestData.DISH1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 10, RestaurantTestData.RESTAURANT_ID, MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DishTestData.DISH_ID, RestaurantTestData.RESTAURANT_ID, MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(MENU_ID), DishTestData.DISH2);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + DishTestData.DISH_ID + 5, RestaurantTestData.RESTAURANT_ID, MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL, RestaurantTestData.RESTAURANT_ID, MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJsonDish(DishTestData.DISH1, DishTestData.DISH2)));
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = DishTestData.getUpdatedDish();
        mockMvc.perform(put(REST_URL + DishTestData.DISH_ID, RestaurantTestData.RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk());

        assertMatch(service.get(DishTestData.DISH_ID, MENU_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Dish expected = DishTestData.getCreatedDish();
        ResultActions action = mockMvc.perform(post(REST_URL, RestaurantTestData.RESTAURANT_ID, MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(MENU_ID), DishTestData.DISH1, DishTestData.DISH2, expected);
    }
}