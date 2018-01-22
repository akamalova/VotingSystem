package ru.voting.web.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.AuthorizedUser;
import ru.voting.model.User;
import ru.voting.service.UserService;
import ru.voting.to.UserTo;

import static ru.voting.util.ValidationUtil.assureIdConsistent;


@RestController
@RequestMapping(UserRestProfileController.REST_URL)
public class UserRestProfileController {
    public static final String REST_URL = "/votingSystem/rest/profile/user";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        log.info("get {}", AuthorizedUser.id());
        return service.get(AuthorizedUser.id());
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        log.info("delete {}", AuthorizedUser.id());
        service.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserTo userTo) {
        log.info("update {} with id={}", userTo, AuthorizedUser.id());
        assureIdConsistent(userTo, AuthorizedUser.id());
        service.update(userTo);
    }
}
