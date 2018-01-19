package ru.testAssignment.voting.web.Dish;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.model.Dish;

import java.util.List;

@RestController
@RequestMapping(DishRestProfileController.REST_URL)
public class DishRestProfileController extends DishRestAdminController {
    public static final String REST_URL = "/votingSystem/rest/profile/restaurants/{restaurantId}/menu/{menuId}/dishes";

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id, @PathVariable int menuId, @PathVariable int restaurantId) {
        return super.get(id, menuId, restaurantId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@PathVariable int menuId, @PathVariable int restaurantId) {
        return super.getAll(menuId, restaurantId);
    }
}
