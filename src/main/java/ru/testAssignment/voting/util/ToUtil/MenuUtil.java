package ru.testAssignment.voting.util.ToUtil;

import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.to.MenuTo;

public class MenuUtil {
    public static Menu createNewFromTo(MenuTo newMenu) {
        return new Menu(newMenu.getDateTime());
    }

    public static MenuTo asTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDateTime());
    }

    public static Menu updateFromTo(Menu menu, MenuTo menuTo) {
        menu.setDateTime(menu.getDateTime());
        return menu;
    }
}
