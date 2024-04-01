package com.example.backend.controllers;

import com.example.backend.dto.AuthRequestDTO;
import com.example.backend.dto.ThemeDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.exceptions.AuthenticationFailedException;
import com.example.backend.exceptions.DuplicateUserException;
import com.example.backend.exceptions.ObjectNotFoundException;
import com.example.backend.models.User;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
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

        Map<String, String> response = new HashMap<>();

        try {
            userService.saveUser(user);
            response.put("message", "Successful");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (DuplicateUserException e) {
            response.put("message", e.getMessage());
            response.put("error-code", e.getErrorCode());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // это 409 код
        }
        catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // это 500 код
        }
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<Map<String, String>> authorize(@RequestBody AuthRequestDTO authRequestDTO) {

        Map<String, String> response = new HashMap<>();

        try {
            userService.verifyCredentials(authRequestDTO);
            response.put("message", "Successful");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (AuthenticationFailedException e) {
            response.put("message", e.getMessage());
            response.put("error-code", e.getErrorCode());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // это 409 код
        }
        catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // это 500 код
        }
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<Map<String, String>> get_user_data(@PathVariable Long userId) {

        Map<String, String> response = new HashMap<>();

        try {
            response = userService.getUserDataByUserId(userId);
            response.put("message", "Successful");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (ObjectNotFoundException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(path = "/{userId}/theme")
    public ResponseEntity<Map<String, String>> change_theme(@PathVariable Long userId, @RequestBody ThemeDTO requestBody) {
        Map<String, String> response = new HashMap<>();

        try {
            userService.changeUserTheme(userId, requestBody.getTheme());

            response.put("message", "Successful");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (ObjectNotFoundException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
