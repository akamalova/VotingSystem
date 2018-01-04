package ru.testAssignment.voting;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.web.RestaurantController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-tools.xml", "spring/mock.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            RestaurantController restaurantController = appCtx.getBean(RestaurantController.class);
            restaurantController.create(new Restaurant(null, "restName", "thai", LocalDateTime.now()));
            System.out.println();


        }
    }
}
