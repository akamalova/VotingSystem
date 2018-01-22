package ru.voting.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.AuthorizedUser;
import ru.voting.model.User;
import ru.voting.model.Vote;
import ru.voting.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(VoteRestAdminController.REST_URL)
public class VoteRestAdminController {
    public static final String REST_URL = "/votingSystem/rest/admin/votes";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id") int id) {
        log.info("get votes {} for user {}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        log.info("getAll", AuthorizedUser.id());
        return service.getAll();
    }

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getbyDate(
            @RequestParam(value = "newDate", required = false) LocalDate date) {
        log.info("get votes by date {} ", date);
        return service.getByDate(date);
    }

    @GetMapping(value = "/voted", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getVotedUsers(
            @RequestParam(value = "newDate", required = false) LocalDate date) {
        log.info("get voted users by date {} ", date);
        return service.getVoted(date);
    }

    @GetMapping(value = "/byUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getUsers(@PathVariable("userId") int userId) {
        log.info("get votes by userId {} ", userId);
        return service.getByUser(userId);
    }
}
