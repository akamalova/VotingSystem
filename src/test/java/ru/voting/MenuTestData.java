package ru.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting.model.Menu;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.voting.web.json.JsonUtil.writeIgnoreProps;

public class MenuTestData {
    public static final int MENU_ID = START_SEQ + 8;

    public static final Menu MENU1 = new Menu(MENU_ID, LocalDate.of(2017, 5, 30));
    public static final Menu MENU2 = new Menu(MENU_ID + 1, LocalDate.of(2015, 5, 30));


    public static Menu getUpdatedMenu() {
        Menu menu = new Menu(MENU_ID, LocalDate.of(2017, 7,30));
        menu.setRestaurantId(RESTAURANT_ID);
        return menu;
    }


    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "restaurantId");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "restaurantId").isEqualTo(expected);
    }

    public static ResultMatcher contentJsonMenu(Menu... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "dishes", "restaurantId"));
    }

    public static ResultMatcher contentJsonMenuOb(Menu expected) {
        return content().json(writeIgnoreProps(expected, "dishes", "restaurantId"));
    }
}
