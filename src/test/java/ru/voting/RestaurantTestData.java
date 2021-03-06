package ru.voting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.voting.model.Restaurant;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.voting.web.json.JsonUtil.writeIgnoreProps;

public class RestaurantTestData {

    public static final int RESTAURANT_ID = START_SEQ + 3;


    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT_ID, "FirstRestaurant", "Swedish");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT_ID + 1, "SecondRestaurant", "Asian");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT_ID + 2, "ThirdRestaurant", "Russian");
    public static final Restaurant RESTAURANT4 = new Restaurant(RESTAURANT_ID + 3, "FourthRestaurant", "Japanese");
    public static final Restaurant RESTAURANT5 = new Restaurant(RESTAURANT_ID + 4, "FifthRestaurant", "Uzbek");

    public static Restaurant getCreatedRestaurant() {
        return new Restaurant(null, "name", "descr");
    }

    public static Restaurant getUpdatedRestaurant() {
        return new Restaurant(RESTAURANT_ID, "Обновленный ресторан", "descr");
    }


    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }

    public static ResultMatcher contentJsonRestaurant(Restaurant... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "menu"));
    }

    public static ResultMatcher contentJsonRestaurant(Restaurant expected) {
        return content().json(writeIgnoreProps(expected, "menu"));
    }
}
