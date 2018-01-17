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
import ru.testAssignment.voting.model.Menu;
import ru.testAssignment.voting.service.MenuService;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;
import static ru.testAssignment.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(MenuRestController.REST_URL)
public class MenuRestController {
    public static final String REST_URL = "/votingSystem/rest/admin/restaurants/{restaurantId}/menu";
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private MenuService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id, @PathVariable int restaurantId){
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return service.get(id, restaurantId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @PathVariable int restaurantId){
        log.info("delete menu {} for restaurant {}", id, restaurantId);
        service.delete(id, restaurantId, AuthorizedUser.id());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(@PathVariable int restaurantId){
        log.info("getAll for restaurant {}", restaurantId);
        return service.getAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu, @PathVariable int restaurantId) {
        checkNew(menu);
        log.info("create {} for restaurant {}", menu, restaurantId);
        Menu created = service.create(menu, restaurantId, AuthorizedUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu update(@RequestBody Menu menu, @PathVariable("id") int id, @PathVariable int restaurantId){
        assureIdConsistent(menu, id);
        log.info("update {} for restaurant {}", menu, restaurantId);
        return service.update(menu, restaurantId, AuthorizedUser.id());
    }

    @RequestMapping(value = "/date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getbyDate(@RequestParam(value = "dateTime", required = false)LocalDate dateTime, @PathVariable int restaurantId){
        log.info("get menu by date {} for restaurant {}", dateTime, restaurantId);
        return new ArrayList<>(service.getByDate(dateTime));
    }
}


