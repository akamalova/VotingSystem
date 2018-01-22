package ru.voting.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.voting.TestUtil;
import ru.voting.model.Restaurant;
import ru.voting.RestaurantTestData;
import ru.voting.UserTestData;
import ru.voting.service.RestaurantService;
import ru.voting.web.Restaurant.RestaurantRestAdminController;
import ru.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.RestaurantTestData.assertMatch;

public class RestaurantRestAdminControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService service;

    private static final String REST_URL = RestaurantRestAdminController.REST_URL + '/';

    @Before
    public void setUp() {
        cacheManager.getCache("restaurants").clear();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJsonRestaurant(RestaurantTestData.RESTAURANT1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }


    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.RESTAURANT_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), RestaurantTestData.RESTAURANT5, RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT4, RestaurantTestData.RESTAURANT2);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }


    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJsonRestaurant(RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT2, RestaurantTestData.RESTAURANT4, RestaurantTestData.RESTAURANT1, RestaurantTestData.RESTAURANT5)));
    }

    @Test
    public void testCreate() throws Exception {
        Restaurant expected = RestaurantTestData.getCreatedRestaurant();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(), expected, RestaurantTestData.RESTAURANT5, RestaurantTestData.RESTAURANT1, RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT4, RestaurantTestData.RESTAURANT2);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdatedRestaurant();
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andExpect(status().isOk());

        assertMatch(service.get(RestaurantTestData.RESTAURANT_ID), updated);
    }
}