package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.web.Restaurant.RestaurantRestProfileController;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.RestaurantTestData.*;
import static ru.testAssignment.voting.UserTestData.ADMIN;
import static ru.testAssignment.voting.UserTestData.USER1;

public class RestaurantRestProfileControllerTest extends AbstractControllerTest{
    private static final String REST_URL = RestaurantRestProfileController.REST_URL + '/';
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_ID)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurant(RESTAURANT1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurant(RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5)));
    }
}