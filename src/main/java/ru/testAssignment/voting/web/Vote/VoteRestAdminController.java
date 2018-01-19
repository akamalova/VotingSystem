package ru.testAssignment.voting.web.Vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.testAssignment.voting.AuthorizedUser;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.service.VoteService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(VoteRestAdminController.REST_URL)
public class VoteRestAdminController {
    public static final String REST_URL = "/votingSystem/rest/admin/votes";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService service;

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@Valid @RequestBody Vote vote, @PathVariable("id") int id) {
        assureIdConsistent(vote, id);
        log.info("update {} for user {}", vote, AuthorizedUser.id());
        return service.update(vote, AuthorizedUser.id(), LocalTime.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote) {
        log.info("create {} for user {}", vote, AuthorizedUser.id());
        Vote enabled = service.create(vote, AuthorizedUser.id(), LocalTime.now());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(enabled.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(enabled);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete vote {} for user {}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id") int id) {
        log.info("get vote {} for user {}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        log.info("getAll for user {}", AuthorizedUser.id());
        return service.getAll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getbyDate(
            @RequestParam(value = "dateTime", required = false) LocalDate dateTime) {
        log.info("get votes by date {} ", dateTime);
        return service.getByDate(dateTime);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/voted", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getVotedUsers(
            @RequestParam(value = "dateTime", required = false) LocalDate dateTime) {
        log.info("get voted users by date {} ", dateTime);
        return service.getVoted(dateTime);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/byUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getUsers(@PathVariable("userId") int userId) {
        log.info("get votes by userId {} ", userId);
        return service.getByUser(userId);
    }
}
