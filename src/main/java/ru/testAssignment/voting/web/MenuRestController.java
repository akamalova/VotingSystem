package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.repository.menu.MenuRepository;

import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(MenuRestController.REST_URL)
public class MenuRestController {
    public static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menu";

    @Autowired
    MenuRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id){
        return repository.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @PathVariable int restaurantId){
        repository.delete(id, restaurantId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(@PathVariable int restaurantId){
        return repository.getAll(restaurantId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu create(@RequestBody Menu menu, @PathVariable int restaurantId){
        return repository.save(menu, restaurantId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu update(@RequestBody Menu menu, @PathVariable("id") int id, @PathVariable int restaurantId){
        assureIdConsistent(menu, id);
        return repository.save(menu, restaurantId);
    }
}


