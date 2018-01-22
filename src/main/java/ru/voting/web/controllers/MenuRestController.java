package ru.voting.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.model.Menu;
import ru.voting.service.MenuService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.voting.util.ValidationUtil.assureIdConsistent;
import static ru.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(MenuRestController.REST_URL)
public class MenuRestController {
    public static final String REST_URL = "/votingSystem/rest/restaurants/{restaurantId}/menu";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id, @PathVariable int restaurantId) {
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return service.get(id, restaurantId);
    }

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getbyDate(@RequestParam(value = "newDate", required = false) LocalDate newDate, @PathVariable int restaurantId) {
        log.info("get menu by date {} for restaurant {}", newDate, restaurantId);
        return new ArrayList<>(service.getByDate(newDate));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return service.getAll(restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restaurantId) {
        checkNew(menu);
        log.info("create {} for restaurant {}", menu, restaurantId);
        Menu created = service.create(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu update(@Valid @RequestBody Menu menu, @PathVariable("id") int id, @PathVariable int restaurantId) {
        assureIdConsistent(menu, id);
        log.info("update {} for restaurant {}", menu, restaurantId);
        return service.update(menu, restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id, @PathVariable int restaurantId) {
        log.info("delete menu {} for restaurant {}", id, restaurantId);
        service.delete(id, restaurantId);
    }
}


