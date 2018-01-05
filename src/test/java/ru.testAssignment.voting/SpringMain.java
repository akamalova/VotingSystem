package ru.testAssignment.voting;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.web.RestaurantController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml", "spring/spring-mvc.xml");
           appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            RestaurantController controller = appCtx.getBean(RestaurantController.class);
            controller.getAll();
            /*controller.create(new Restaurant(null, "Name", "disc",  LocalDateTime.now()));*/
            System.out.println();


        }
    }
}
