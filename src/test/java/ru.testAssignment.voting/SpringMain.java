package ru.testAssignment.voting;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.web.DishController;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml", "spring/mock.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DishController controller = appCtx.getBean(DishController.class);
            controller.create(new Dish(null, "Name", 15.5), 100003);
            System.out.println();


        }
    }
}
