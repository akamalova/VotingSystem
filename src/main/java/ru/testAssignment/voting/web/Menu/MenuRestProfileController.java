package ru.testAssignment.voting.web.Menu;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.model.Menu;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(MenuRestProfileController.REST_URL)
public class MenuRestProfileController extends MenuRestAdminController{
    public static final String REST_URL = "/votingSystem/rest/profile/restaurants/{restaurantId}/menu";


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id, @PathVariable int restaurantId){
        return super.get(id, restaurantId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(@PathVariable int restaurantId){
        return super.getAll(restaurantId);
    }

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getbyDate(@RequestParam(value = "dateTime", required = false)LocalDate dateTime, @PathVariable int restaurantId){
        return super.getbyDate(dateTime, restaurantId);
    }
}


