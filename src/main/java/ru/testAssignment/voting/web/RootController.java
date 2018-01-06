package ru.testAssignment.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.testAssignment.voting.service.RestaurantService;
import ru.testAssignment.voting.service.UserService;

@Controller
public class RootController {

    @Autowired
    UserService userService;


}
