package com.example.backend.controllers;

import com.example.backend.dto.AuthRequestDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.exceptions.DublicateUserException;
import com.example.backend.models.User;
import com.example.backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDTO userDTO) {

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

        HashMap<String, String> response = new HashMap<>();

        try {
            userService.saveUser(user);
            response.put("message", "Successful");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (DublicateUserException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // это 409 код
        }
        catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // это 500 код
        }
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<Map<String, String>> authorize(@RequestBody AuthRequestDTO authRequestDTO) {

        HashMap<String, String> response = new HashMap<>();

        try {
            userService.verifyCredentials(authRequestDTO);
            response.put("message", "Successful");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (DublicateUserException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // это 409 код
        }
        catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // это 500 код
        }
    }
}
