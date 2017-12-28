package ru.testAssignment.voting.util;

import ru.testAssignment.voting.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MenuUtil {
    public static void main(String[] args) {
        int id = 0;
        Map<String, Double> dishesMap = new HashMap<>();
        dishesMap.put("Soup", 15.3);
        dishesMap.put("Meat", 25.7);
        dishesMap.put("Juice", 15.00);

        List<Restaurant> restaurantList = Arrays.asList(
                new Restaurant("FirstRestaurant", id++, "Swedish", dishesMap, 20),
                new Restaurant("SecondRestaurant", id++, "Asian", dishesMap, 5),
                new Restaurant("ThirdRestaurant", id++, "Russian", dishesMap, 10),
                new Restaurant("FourthRestaurant", id++, "Japanese", dishesMap, 3),
                new Restaurant("FourthRestaurant", id++, "Japanese", LocalDateTime.of(2015, Month.MAY, 31, 13, 0), dishesMap, 3),
                new Restaurant("FifthRestaurant", id, "Uzbek", dishesMap));


       // getVotesWinner(restaurantList, LocalDate.now()).forEach(System.out::println);
        getVotesHistory(restaurantList).forEach((b1, b2) -> System.out.println(b1 + b2.toString()));
    }

    private static Map<LocalDate, List<Restaurant>> getVotesHistory(List<Restaurant> restaurantList) {
        return restaurantList.stream()
                .collect(Collectors.groupingBy(Restaurant::getDate));
    }

    private static List<Restaurant> getVotesWinner(List<Restaurant> restaurantList, LocalDate localDate) {
        return restaurantList.stream()
                .filter(restaurant -> restaurant.getDateTime().toLocalDate().equals(localDate))
                .sorted((o1, o2) -> Integer.compare(o2.getVotesNum(), o1.getVotesNum()))
                .collect(Collectors.toList());
    }
}
