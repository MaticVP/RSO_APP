package com.example.RSO.Service.Controllers;

import com.example.RSO.Service.Entity.User;
import com.example.RSO.Service.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;

@Controller
//@RequestMapping(path="api/users/graphql")
public class UserGraphQLController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public User getUserByUsername(@Argument String username) {
        logger.info("Entering (getUserByUsername)");
        logger.info("Exiting (getUserByUsername)");
        return userRepository.findByUsername(username);

    }
}
