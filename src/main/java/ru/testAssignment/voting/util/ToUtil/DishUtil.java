package ru.testAssignment.voting.util.ToUtil;

import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.to.DishTo;

public class DishUtil {
    public static Dish createNewFromTo(DishTo newDish) {
        return new Dish(newDish.getName(), newDish.getPrice());
    }

    public static DishTo asTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
       dish.setName(dish.getName());
       dish.setPrice(dish.getPrice());
        return dish;
    }
}
