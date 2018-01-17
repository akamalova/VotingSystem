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
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.testAssignment.voting.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    public static final String REST_URL = "/votingSystem/rest/admin/votes";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService service;

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@RequestBody Vote vote, @PathVariable("id")int id){
        assureIdConsistent(vote, id);
        log.info("update {} for user {}", vote, AuthorizedUser.id());
        return service.update(vote, AuthorizedUser.id(), LocalTime.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdate(@RequestBody Vote vote) {
        log.info("create {} for user {}", vote, AuthorizedUser.id());
        Integer voteId = service.voteId(LocalDate.now(), AuthorizedUser.id());

        Vote enabled = voteId == null ? service.create(vote, AuthorizedUser.id(), LocalTime.now()) : update(vote, voteId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(enabled.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(enabled);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        log.info("delete vote {} for user {}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id")int id){
        log.info("get vote {} for user {}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll(){
        log.info("getAll for user {}", AuthorizedUser.id());
        return service.getAll(AuthorizedUser.id());
    }

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getbyDate(
            @RequestParam(value = "dateTime", required = false)LocalDate dateTime){
        log.info("get votes by date {} ", dateTime);
        return service.getByDate(dateTime);
    }

    @GetMapping(value = "/voted", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getVotedUsers(
            @RequestParam(value = "dateTime", required = false)LocalDate dateTime){
        log.info("get voted users by date {} ", dateTime);
        return service.getVoted(dateTime);
    }
}
