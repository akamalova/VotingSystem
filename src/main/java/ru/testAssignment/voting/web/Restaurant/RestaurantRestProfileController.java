package ru.testAssignment.voting.web.Restaurant;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.model.Restaurant;

import java.util.List;

@RestController
@RequestMapping(RestaurantRestProfileController.REST_URL)
public class RestaurantRestProfileController extends RestaurantRestAdminController {

    public static final String REST_URL = "/votingSystem/rest/profile/restaurants";

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return super.getAll();
    }
}
