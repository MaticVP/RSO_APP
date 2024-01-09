package com.example.RSO.Service.Controllers;

import com.example.RSO.Metrics.TimerMetric;
import com.example.RSO.Metrics.UploadCounter;
import com.example.RSO.Service.Entity.User;
import com.example.RSO.Service.Repository.UserRepository;
import com.example.RSO.Service.Service.DropboxService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.coobird.thumbnailator.Thumbnails;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.dropbox.core.v2.DbxClientV2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping(path="api/users")
@CrossOrigin(origins = "http://localhost:3000")
@OpenAPIDefinition(
        info = @Info(
                title = "User API",
                version = "1.0",
                description = "API responsible for handling request related to users accounts (changing user name, photo, login, etc.)"
        )
)
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";


    private final UploadCounter uploadCounter;

    private final TimerMetric timerMetric;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DropboxService dropboxService;

    @Autowired
    public UserController(UploadCounter customMetrics, TimerMetric timer) {
        uploadCounter = customMetrics;
        timerMetric = timer;
    }

    @Operation(summary = "Add user or register user")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AbstractReadWriteAccess.Item.class)))
    @PostMapping(path="/add")
    public @ResponseBody
    String addNewUser (@RequestParam String username, @RequestParam String password) {

        User user = new User(username, password,"");
        String log_output = "Added user with info: " + username.toString();
        logger.info(log_output);
        userRepository.save(user);
        return "Saved";
    }

    @PostMapping(path="/update-profile")
    public @ResponseBody
    String update (@RequestParam String username, @RequestParam String password, @RequestParam String description) {

        User user = userRepository.findByUsername(username);
        user.setUsername(username);
        user.setProfileDescription(description);
        user.setPassword(password);
        userRepository.save(user);
        return "updated";

    }

    @PostMapping(path="/upload-photo")
    @ResponseBody
    public String uploadPhoto (@RequestParam String username, @RequestParam("image") MultipartFile file) throws IOException {
        timerMetric.startTimer();
        uploadCounter.recordUploadMetric();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream())
                .size(32, 32)
                .outputFormat("png") // You can change the output format if needed
                .toOutputStream(outputStream);
        dropboxService.uploadFile(new ByteArrayInputStream(outputStream.toByteArray()), file.getOriginalFilename(),username);
        logger.info("drop box added image");
        timerMetric.endTimer();
        return "photo added";
    }

    @GetMapping(path="/get-photo")
    public @ResponseBody
    ResponseEntity<byte[]> getPhoto(@RequestParam String username) throws IOException {
        uploadCounter.recordUploadMetric();
        byte[] image = dropboxService.getPhoto(username);
        logger.info("drop box got image");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping(path="/get-id")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    Long getID (@RequestParam String username) throws IOException {
        User user = userRepository.findByUsername(username);
        return  user.getId();
    }

    @GetMapping(path="/get-about")
    public @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    String getAbout (@RequestParam String username) throws IOException {
        User user = userRepository.findByUsername(username);
        return user.getProfileDescription();
    }

    @GetMapping(path="/login")
    @Operation(summary = "Check if user is valid")
    @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(schema = @Schema(implementation = AbstractReadWriteAccess.Item.class)))
    @Hidden
    public @ResponseBody
    int login (@RequestParam String username, @RequestParam String password) {
        logger.info("Entering (login)");
        User user = userRepository.findByUsername(username);
        if(user != null && Objects.equals(user.getPassword(), password)) {
            logger.info("Exiting (login):user was found");
            return 0;
        }

        logger.info("Exiting (login):user was not found");

        return -1;
    }

    @GetMapping(path="/recover")
    public @ResponseBody
    String recoverUser (@RequestParam String username) {
        logger.info("Entering (recoverUser)");
        User user = userRepository.findByUsername(username);
        if(user != null) {
            logger.debug("user was found");
            return user.getPassword();
        }

        logger.warn("user not found");
        logger.info("Exiting (recoverUser)");
        return "";
    }



    @DeleteMapping(path="/deleteAll")
    public String deleteAllUsers() {
        logger.info("Entering (deleteAllUsers)");
        logger.info("Deleting users");
        userRepository.deleteAll();
        logger.info("Exiting (deleteAllUsers)");
        return "Deleted all users";

    }

    @DeleteMapping(path="/delete")
    public String deleteAllUsers(String username) {
        logger.info("Entering (deleteAllUsers)");
        userRepository.removeByUsername(username);
        logger.info("Exiting (deleteAllUsers)");
        return "Deleted deleted user "+username;

    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        logger.info("Entering (getAllUsers)");
        logger.info("Getting all users");
        for (int i=0;i<100;i++)
            userRepository.findAll();
        logger.info("Exiting (getAllUsers)");
        return userRepository.findAll();

    }
}


