package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.service.MenuService;

import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;

@Controller
public class MenuController {

    @Autowired
    MenuService service;

    public Menu get(int id){
        return service.get(id);
    }

    public void delete(int id, int restaurantId){
        service.delete(id, restaurantId);
    }

    public List<Menu> getAll(int restaurantId){
        return service.getAll(restaurantId);
    }

    public Menu create(Menu menu, int restaurantId){
        return service.create(menu, restaurantId);
    }

    public Menu update(Menu menu, int id, int restaurantId){
        assureIdConsistent(menu, id);
        return service.update(menu, restaurantId);
    }
}


