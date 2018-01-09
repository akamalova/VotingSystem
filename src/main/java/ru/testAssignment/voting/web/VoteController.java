package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.testAssignment.voting.AuthorizedUser;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(VoteController.REST_URL)
public class VoteController {
    public static final String REST_URL = "/rest/admin/votes";

    @Autowired
    VoteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@RequestBody Vote vote){

        int userId = AuthorizedUser.id();
        return service.update(vote, userId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote save(@RequestBody Vote vote){
        int userId = AuthorizedUser.id();
        return service.save(vote, userId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id")int id){
        int userId = AuthorizedUser.id();
        return service.get(id, userId);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll(){
        int userId = AuthorizedUser.id();
        return service.getAll(userId);
    }

    @RequestMapping(value = "/getByDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getbyDate(LocalDate date){
        int userId = AuthorizedUser.id();
        return service.getByDate(date, userId);
    }

}
