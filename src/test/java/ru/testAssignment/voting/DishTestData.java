package ru.testAssignment.voting;

import ru.testAssignment.voting.model.Dish;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.testAssignment.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {

    public static final int DISH_ID = START_SEQ + 14;

    public static final Dish DISH1 = new Dish(DISH_ID, "Soup", 25.3);
    public static final Dish DISH2 = new Dish(DISH_ID + 1, "Meat", 60.5);

    public static Dish getCreatedDish(){
        return new Dish(null, "newDish", 20.00);
    }

    public static Dish getUpdatedDish() {
        return new Dish(DISH_ID, "Обновленное блюдо", 15.35);
    }


    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }
}
