package ru.testAssignment.voting;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.web.Dish.DishRestProfileController;
import java.util.Arrays;
import java.util.List;

import static ru.testAssignment.voting.MenuTestData.MENU_ID;
import static ru.testAssignment.voting.RestaurantTestData.RESTAURANT_ID;
import static ru.testAssignment.voting.TestUtil.mockAuthorize;
import static ru.testAssignment.voting.UserTestData.USER1;


public class SpringMain {
    public static void main(String[] args) {

        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml", "spring/spring-mvc.xml");
            appCtx.refresh();
            mockAuthorize(USER1);

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DishRestProfileController controller = appCtx.getBean(DishRestProfileController.class);

            List<Dish> list = controller.getAll(MENU_ID, RESTAURANT_ID);
            list.forEach(System.out::println);


        }
    }
}
