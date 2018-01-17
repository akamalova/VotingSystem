package ru.testAssignment.voting;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.web.Dish.DishRestAdminController;

import java.util.Arrays;
import java.util.List;


public class SpringMain {
    public static void main(String[] args) {

        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml", "spring/spring-mvc.xml");
            appCtx.refresh();


            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DishRestAdminController controller = appCtx.getBean(DishRestAdminController.class);

            List<Dish> restaurantList = controller.getAll(100008, 100003);
            restaurantList.forEach(System.out::println);


        }
    }
}
