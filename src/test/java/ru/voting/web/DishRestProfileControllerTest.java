package ru.voting.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.voting.TestUtil;
import ru.voting.*;
import ru.voting.web.Dish.DishRestProfileController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishRestProfileControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishRestProfileController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DishTestData.DISH_ID, RestaurantTestData.RESTAURANT_ID, MenuTestData.MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJsonDish(DishTestData.DISH1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL, RestaurantTestData.RESTAURANT_ID, MenuTestData.MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJsonDish(DishTestData.DISH1, DishTestData.DISH2)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DishTestData.DISH_ID, RestaurantTestData.RESTAURANT_ID, MenuTestData.MENU_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}