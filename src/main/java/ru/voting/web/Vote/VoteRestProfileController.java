package ru.voting.web.Vote;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.voting.model.Vote;

import javax.validation.Valid;

@RestController
@RequestMapping(VoteRestProfileController.REST_URL)
public class VoteRestProfileController extends VoteRestAdminController {
    public static final String REST_URL = "/votingSystem/rest/profile/vote";

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@RequestBody Vote vote, @PathVariable("id") int id) {
        return super.update(vote, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote) {
        return super.create(vote);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id") int id) {
        return super.get(id);
    }
}
