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
import ru.voting.model.Vote;
import ru.voting.service.RestaurantService;
import ru.voting.service.VoteService;
import ru.voting.util.ValidationUtil;
import ru.voting.web.controllers.RestaurantRestController;
import ru.voting.web.json.JsonUtil;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.RestaurantTestData.*;
import static ru.voting.TestUtil.readFromJson;
import static ru.voting.TestUtil.userHttpBasic;
import static ru.voting.UserTestData.*;
import static ru.voting.VoteTestData.*;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Before
    public void setUp() {
        cacheManager.getCache("restaurants").clear();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurant(RESTAURANT1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }


    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), RESTAURANT5, RESTAURANT3, RESTAURANT4, RESTAURANT2);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJsonRestaurant(RestaurantTestData.RESTAURANT3, RestaurantTestData.RESTAURANT2, RestaurantTestData.RESTAURANT4, RESTAURANT1, RESTAURANT5)));
    }

    @Test
    public void testCreate() throws Exception {
        Restaurant expected = RestaurantTestData.getCreatedRestaurant();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(restaurantService.getAll(), expected, RESTAURANT5, RESTAURANT1, RESTAURANT3, RESTAURANT4, RESTAURANT2);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        assertMatch(restaurantService.get(RESTAURANT_ID), updated);
    }

    @Test
    public void createOrUpdateVote() throws Exception{

        Vote created = getCreatedVote();
        ResultActions actionCreate = mockMvc.perform(post(REST_URL + "vote?restaurantId=" + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote returned = readFromJson(actionCreate, Vote.class);
        created.setId(returned.getId());
        assertMatch(voteService.getByUser(USER_ID), VOTE1, VOTE2, created);

        ValidationUtil.setTimeLimit(LocalTime.MAX);
        ResultActions actionUpdate = mockMvc.perform(post(REST_URL + "vote?restaurantId=" + (RESTAURANT_ID + 2))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk());
        created.setRestaurantId(RESTAURANT_ID + 2);
        assertMatch(voteService.getByUser(USER_ID), VOTE1, VOTE2, created);
    }
}