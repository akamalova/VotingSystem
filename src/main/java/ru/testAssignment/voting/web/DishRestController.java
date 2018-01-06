package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.service.DishService;

import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController {
    public static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menus/{menuId}/dishes";

    @Autowired
    private DishService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id, @PathVariable int menuId){
        return service.get(id, menuId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @PathVariable int menuId) {
        service.delete(id, menuId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@PathVariable int menuId) {
        return service.getAll(menuId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish update(@RequestBody Dish dish, @PathVariable("id") int id, @PathVariable int menuId) {
        assureIdConsistent(dish, id);
        return service.update(dish, menuId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@RequestBody Dish dish, @PathVariable int menuId) {
        Dish created = service.create(dish, menuId);
        return created;

       /* URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(menuId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);*/
    }
}
