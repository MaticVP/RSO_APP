package com.example.RSO.Service.Controllers;

import com.example.RSO.Service.Entity.User;
import com.example.RSO.Service.Repository.UserRepository;
import com.example.RSO.Service.Service.DropboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.dropbox.core.v2.DbxClientV2;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping(path="api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DropboxService dropboxService = new DropboxService();

    public UserController(DbxClientV2 client) {

    }


    @PostMapping(path="/add")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    String addNewUser (@RequestParam String username, @RequestParam String password) {

        User user = new User(username, password,"");
        String log_output = "Added user with info: " + username.toString();
        logger.info(log_output);
        userRepository.save(user);
        return "Saved";
    }

    @PostMapping(path="/update-profile")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    String update (@RequestParam String username, @RequestParam String password, @RequestParam String description) {

        User user = userRepository.findByUsername(username);
        user.setUsername(username);
        user.setProfileDescription(description);
        user.setPassword(password);
        userRepository.save(user);
        return "updated";
    }

    @PostMapping(path="/upload-photo")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    String uploadPhoto (@RequestParam String username, @RequestParam("image") MultipartFile file) throws IOException {
        dropboxService.uploadFile(file.getInputStream(), file.getOriginalFilename(),username);
        logger.info("drop box added image");
        return "photo added";
    }

    @GetMapping(path="/get-photo")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    ResponseEntity<byte[]> getPhoto (@RequestParam String username) throws IOException {
        byte[] image = dropboxService.getPhoto(username);
        logger.info("drop box got image");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping(path="/login")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    int login (@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if(user != null && Objects.equals(user.getPassword(), password)) {
            logger.debug("user was found");
            return 0;
        }

        logger.warn("user not found");

        return -1;
    }

    @GetMapping(path="/recover")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    String recoverUser (@RequestParam String username) {

        User user = userRepository.findByUsername(username);
        if(user != null) {
            logger.debug("user was found");
            return user.getPassword();
        }

        logger.warn("user not found");

        return "";
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


