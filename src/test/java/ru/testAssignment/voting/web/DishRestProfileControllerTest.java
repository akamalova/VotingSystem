package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.web.Dish.DishRestProfileController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.DishTestData.*;
import static ru.testAssignment.voting.MenuTestData.MENU_ID;
import static ru.testAssignment.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.testAssignment.voting.TestUtil.userHttpBasic;
import static ru.testAssignment.voting.UserTestData.USER1;

public class DishRestProfileControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishRestProfileController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonDish(DISH1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL, RESTAURANT_ID, MENU_ID)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonDish(DISH1, DISH2)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH_ID, RESTAURANT_ID, MENU_ID)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}