package ru.voting.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.AuthorizedUser;
import ru.voting.model.User;
import ru.voting.to.UserTo;

@RestController
@RequestMapping(UserRestProfileController.REST_URL)
public class UserRestProfileController extends UserAbstractController {
    public static final String REST_URL = "/votingSystem/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserTo userTo) {
        super.update(userTo, AuthorizedUser.id());
    }
}