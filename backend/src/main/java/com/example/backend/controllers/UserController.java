package com.example.backend.controllers;

import com.example.backend.dto.UserDTO;
import com.example.backend.models.User;
import com.example.backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAgeLimit(userDTO.getAgeLimit());
        user.setGender(userDTO.getGender());
        user.setAcceptRules(userDTO.getAcceptRules());
        user.setTheme("light");
        user.setCreated(LocalDateTime.now());

        userService.saveUser(user);
        // как-то обрабатывать ответ от сервиса??? или через exceptions делать

        return ResponseEntity.status(HttpStatus.OK).body("Successful");
    }
}
