package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import ru.testAssignment.voting.AuthorizedUser;
import ru.testAssignment.voting.model.Restaurant;
import ru.testAssignment.voting.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    public Restaurant get(int id){
        int userId = AuthorizedUser.id();
        return service.get(id);
    }

    public void delete(int id){
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
    }

    public List<Restaurant> getAll() {
        int userId = AuthorizedUser.id();
        return service.getAll();
    }

    public Restaurant create(Restaurant restaurant) {
        int userId = AuthorizedUser.id();
        return service.create(restaurant, userId);
    }

    public void update(Restaurant restaurant, int id) {
        int userId = AuthorizedUser.id();
        service.update(restaurant, userId);
    }
}
