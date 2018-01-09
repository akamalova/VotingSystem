package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.AuthorizedUser;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.repository.vote.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {
    public static final String REST_URL = "/rest/admin/votes";

    @Autowired
    VoteRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@RequestBody Vote vote){

        int userId = AuthorizedUser.id();
        return repository.save(vote, userId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote save(@RequestBody Vote vote){
        int userId = AuthorizedUser.id();
        return repository.save(vote, userId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id")int id){
        int userId = AuthorizedUser.id();
        return repository.get(id, userId);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll(){
        int userId = AuthorizedUser.id();
        return repository.getAll(userId);
    }

    @RequestMapping(value = "/getByDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getbyDate(LocalDate date){
        int userId = AuthorizedUser.id();
        return repository.getByDate(date, userId);
    }

}
