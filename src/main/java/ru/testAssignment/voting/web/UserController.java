package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.testAssignment.voting.model.User;
import ru.testAssignment.voting.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    public List<User> getNotVoted(){
        return service.getNotVoted();
    }
}
