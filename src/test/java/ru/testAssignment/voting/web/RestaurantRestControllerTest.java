package ru.testAssignment.voting.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.service.RestaurantService;
import ru.testAssignment.voting.web.json.JsonUtil;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.RestaurantTestData.*;
import static ru.testAssignment.voting.UserTestData.ADMIN_ID;

public class RestaurantRestControllerTest extends AbstractControllerTest{

    @Autowired
    private RestaurantService service;

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Before
    public void setUp() {
        cacheManager.getCache("restaurants").clear();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurant(RESTAURANT1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT5);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurant(RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5)));
    }

    @Test
    public void testCreate() throws Exception {
        Restaurant expected = getCreatedRestaurant();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(), expected, RESTAURANT3, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        mockMvc.perform(put(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(service.get(RESTAURANT_ID), updated);
    }
}