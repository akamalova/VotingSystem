package ru.testAssignment.voting.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.testAssignment.voting.TestUtil;
import ru.testAssignment.voting.web.Menu.MenuRestProfileController;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.testAssignment.voting.MenuTestData.*;
import static ru.testAssignment.voting.MenuTestData.MENU2;
import static ru.testAssignment.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.testAssignment.voting.UserTestData.ADMIN;
import static ru.testAssignment.voting.UserTestData.USER1;

public class MenuRestProfileControllerTest extends AbstractControllerTest{
    private static final String REST_URL = MenuRestProfileController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MENU_ID, RESTAURANT_ID)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonMenuOb(MENU1));
    }

   @Test
    public void testGetAll() throws Exception {

        TestUtil.print(mockMvc.perform(get(REST_URL, RESTAURANT_ID)
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonMenu(MENU1, MENU2)));
    }

    @Test
    public void testGetByDate() throws Exception {

        mockMvc.perform(get(REST_URL + "date", RESTAURANT_ID)
                .param("dateTime", "2017-05-30")
                .with(TestUtil.userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonMenu(MENU1));
    }

}