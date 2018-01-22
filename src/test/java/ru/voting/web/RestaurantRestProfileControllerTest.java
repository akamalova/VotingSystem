package ru.voting.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting.TestUtil;
import ru.voting.RestaurantTestData;
import ru.voting.UserTestData;
import ru.voting.web.Restaurant.RestaurantRestProfileController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestProfileControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantRestProfileController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJsonRestaurant(RestaurantTestData.RESTAURANT1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJsonRestaurant(RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT2, RestaurantTestData.RESTAURANT4, RestaurantTestData.RESTAURANT1, RestaurantTestData.RESTAURANT5)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.RESTAURANT_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER1)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}