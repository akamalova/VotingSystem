package ru.testAssignment.voting;

import ru.testAssignment.voting.model.Menu;

import java.time.LocalDateTime;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.testAssignment.voting.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final int MENU_ID = START_SEQ + 8;

    public static final Menu MENU1 = new Menu(MENU_ID, LocalDateTime.of(2017,05,30,10,00, 00));
    public static final Menu MENU2 = new Menu(MENU_ID + 1, LocalDateTime.of(2015,05,30,10,00,00));

    public static Menu getCreatedMenu(){
        return new Menu(null, LocalDateTime.of(2017,05,30,10,00, 00));
    }

    public static Menu getUpdatedMenu() {
        return new Menu(MENU_ID, LocalDateTime.of(2027,05,30,10,00, 00));
    }


    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "restaurant").isEqualTo(expected);
    }
}
