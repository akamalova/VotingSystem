package ru.testAssignment.voting;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.web.DishController;
import ru.testAssignment.voting.web.RestaurantController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {

        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml", "spring/spring-mvc.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DishController controller = appCtx.getBean(DishController.class);

            controller.create(new Dish("Name", 15.00), 100008);

            List<Dish> restaurantList = controller.getAll(100008);
            restaurantList.forEach(System.out::println);


        }
    }
}
