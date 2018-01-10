package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.AuthorizedUser;
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.service.MenuService;

import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;
import static ru.testAssignment.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(MenuRestController.REST_URL)
public class MenuRestController {
    public static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menu";

    @Autowired
    MenuService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id){
        return service.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @PathVariable int restaurantId){
        service.delete(id, restaurantId, AuthorizedUser.id());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(@PathVariable int restaurantId){
        return service.getAll(restaurantId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu create(@RequestBody Menu menu, @PathVariable int restaurantId){
        checkNew(menu);
        return service.create(menu, restaurantId, AuthorizedUser.id());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu update(@RequestBody Menu menu, @PathVariable("id") int id, @PathVariable int restaurantId){
        assureIdConsistent(menu, id);
        return service.update(menu, restaurantId, AuthorizedUser.id());
    }
}


