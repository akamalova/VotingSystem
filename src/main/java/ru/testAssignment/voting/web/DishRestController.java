package ru.testAssignment.voting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public static final String REST_URL = "/votingSystem/rest/admin/restaurants/{restaurantId}/menu/{menuId}/dishes";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id, @PathVariable int menuId, @PathVariable int restaurantId){
        log.info("get dish {} for menu {}", id, menuId);
        return service.get(id, menuId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("delete dish {} for menu {}", id, menuId);
        service.delete(id, menuId, AuthorizedUser.id());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("getAll for for menu {}", menuId);
        return service.getAll(menuId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
        public Dish update(@RequestBody Dish dish, @PathVariable("id") int id, @PathVariable int menuId, @PathVariable int restaurantId) {
            assureIdConsistent(dish, id);
            log.info("update {} for menu {}", dish, menuId);
            return service.update(dish, menuId, AuthorizedUser.id());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish, @PathVariable int menuId, @PathVariable int restaurantId) {
        checkNew(dish);
        log.info("create {} for menu {}", dish, menuId);
        Dish created = service.create(dish, menuId, AuthorizedUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menuId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
