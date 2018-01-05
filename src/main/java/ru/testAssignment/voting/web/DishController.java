package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.service.DishService;

import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;

@Controller
public class DishController {

    @Autowired
    DishService service;

    public Dish get(int id, int restaurantId){
        return service.get(id, restaurantId);
    }

    public void delete(int id, int restaurantId){
        service.delete(id, restaurantId);
    }

    public List<Dish> getAll(int restaurantId){
        return service.getAll(restaurantId);
    }

    public Dish create(Dish dish, int restaurantId){
        return service.create(dish, restaurantId);
    }

    public Dish update(Dish dish, int id, int restaurantId){
        assureIdConsistent(dish, id);
        return service.update(dish, restaurantId);
    }
}


