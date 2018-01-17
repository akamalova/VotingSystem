package ru.testAssignment.voting.util.ToUtil;

import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.to.RestaurantTo;

public class RestaurantUtil {
    public static Restaurant createNewFromTo(RestaurantTo newRestaurant) {
        return new Restaurant(newRestaurant.getName(), newRestaurant.getDescription());
    }

    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getDescription());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        restaurant.setDescription(restaurantTo.getDescription());
        return restaurant;
    }
}
