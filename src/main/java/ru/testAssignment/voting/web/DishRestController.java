package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.testAssignment.voting.AuthorizedUser;
import ru.testAssignment.voting.model.Dish;
import ru.testAssignment.voting.service.DishService;

import java.net.URI;
import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;
import static ru.testAssignment.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController {
    public static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menu/{menuId}/dishes";

    @Autowired
    private DishService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id, @PathVariable int menuId, @PathVariable int restaurantId){
        return service.get(id, menuId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @PathVariable int menuId, @PathVariable int restaurantId) {
        service.delete(id, menuId, AuthorizedUser.id());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@PathVariable int menuId, @PathVariable int restaurantId) {
        return service.getAll(menuId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
        public Dish update(@RequestBody Dish dish, @PathVariable("id") int id, @PathVariable int menuId, @PathVariable int restaurantId) {
            assureIdConsistent(dish, id);
            return service.update(dish, menuId, AuthorizedUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish, @PathVariable int menuId, @PathVariable int restaurantId) {
        checkNew(dish);
        Dish created = service.create(dish, menuId, AuthorizedUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menuId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
