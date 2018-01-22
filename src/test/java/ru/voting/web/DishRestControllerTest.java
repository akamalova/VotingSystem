package ru.voting.web;

import org.junit.Before;
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
import ru.voting.web.controllers.DishRestController;
import ru.voting.web.json.JsonUtil;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.DishTestData.*;
import static ru.voting.MenuTestData.MENU_ID;
import static ru.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.voting.TestUtil.userHttpBasic;
import static ru.voting.UserTestData.ADMIN;
import static ru.voting.UserTestData.USER1;

public class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    private DishService service;

    @Before
    public void setUp() {
        cacheManager.getCache("dish").clear();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk())

                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJsonDish(DISH1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 10, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
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
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH_ID + 5, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDeleteInternalServerError() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJsonDish(DISH1, DISH2)));
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = getUpdatedDish();
        mockMvc.perform(put(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        assertMatch(service.get(DISH_ID, MENU_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Dish expected = getCreatedDish();
        ResultActions action = mockMvc.perform(post(REST_URL, RESTAURANT_ID, MENU_ID)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(MENU_ID), DISH1, DISH2, expected);
    }
}

