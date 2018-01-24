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
import ru.voting.AuthorizedUser;
import ru.voting.model.Restaurant;
import ru.voting.model.Vote;
import ru.voting.service.RestaurantService;
import ru.voting.service.VoteService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.voting.util.ValidationUtil.assureIdConsistent;
import static ru.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {

    public static final String REST_URL = "/votingSystem/rest/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdateVote(@RequestParam int restaurantId) {
        Vote newVote = new Vote(LocalDate.now(), restaurantId);

        Vote checkVote = voteService.getByDate(LocalDate.now()).stream()
                .filter(vote -> vote.getUser().getId() == AuthorizedUser.id())
                .findFirst().orElse(null);

        if (checkVote == null) {
            log.info("create vote {} for user {}", newVote, AuthorizedUser.id());
            Vote enabled = voteService.create(newVote, AuthorizedUser.id(), LocalTime.now());

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(enabled.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(enabled);
        } else {

            assureIdConsistent(newVote, checkVote.getId());
            log.info("update {} for user {}", newVote, AuthorizedUser.id());
            voteService.update(newVote, AuthorizedUser.id(), LocalTime.now());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete restaurant {} for user {}", id);
        restaurantService.delete(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        log.info("create {}", restaurant);
        Restaurant created = restaurantService.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        assureIdConsistent(restaurant, id);
        log.info("update {} ", restaurant);
        restaurantService.update(restaurant);
    }


}
