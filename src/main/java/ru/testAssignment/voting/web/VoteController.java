package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.testAssignment.voting.model.Vote;
import ru.testAssignment.voting.service.VoteService;

import java.util.List;

@Controller
public class VoteController {

    @Autowired
    VoteService service;

    public Vote update(Vote vote){
        return service.update(vote);
    }

    public Vote save(Vote vote){
        return service.save(vote);
    }
    public Vote get(int id, int userId){
        return service.get(id, userId);
    }

    public List<Vote> getAll(int userId){
        return service.getAll(userId);
    }
}
