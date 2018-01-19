package ru.testAssignment.voting.web.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.service.UserService;
import ru.testAssignment.voting.to.UserTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.testAssignment.voting.util.UserUtil.createNewFromTo;
import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;
import static ru.testAssignment.voting.util.ValidationUtil.checkNew;


@RestController
@RequestMapping(UserRestAdminController.REST_URL)
public class UserRestAdminController {
    public static final String REST_URL = "/votingSystem/rest/admin/users";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createOrUpdate(@Valid @RequestBody UserTo userTo) {

        if (userTo.isNew()) {
            log.info("create {}", userTo);
            checkNew(createNewFromTo(userTo));
            User created = service.create(createNewFromTo(userTo));
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else {
            log.info("update {} with id={}", userTo, userTo.getId());
            assureIdConsistent(userTo, userTo.getId());
            service.update(userTo);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable("id") int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByEmail(@RequestParam("email") String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getByDate(
            @RequestParam(value = "dateTime", required = false) LocalDate dateTime) {
        log.info("getByDate {}", dateTime);
        return service.getByDate(dateTime);
    }

    @PostMapping(value = "/{id}")
    public void enable(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {

        log.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }

}
