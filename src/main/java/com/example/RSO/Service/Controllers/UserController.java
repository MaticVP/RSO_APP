package com.example.RSO.Service.Controllers;

import com.example.RSO.Service.Entity.User;
import com.example.RSO.Service.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(path="api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    String addNewUser (@RequestParam String username, @RequestParam String password) {

        User user = new User(username, password);
        String log_output = "Added user with info: " + username.toString();
        logger.info(log_output);
        userRepository.save(user);
        return "Saved";
    }


    @DeleteMapping(path="/deleteAll")
    public String deleteAllUsers() {
        logger.info("Deleting users");
        userRepository.deleteAll();
        return "Deleted all users";

    }

    @DeleteMapping(path="/delete")
    public String deleteAllUsers(String username) {
        userRepository.removeByUsername(username);
        return "Deleted deleted user "+username;

    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        logger.info("Getting all users");
        return userRepository.findAll();

    }
}


