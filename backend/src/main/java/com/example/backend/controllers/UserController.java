package com.example.backend.controllers;

import com.example.backend.dto.AuthRequestDTO;
import com.example.backend.dto.ResponseBodyDTO;
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
    public ResponseEntity<ResponseBodyDTO> register(@RequestBody UserDTO userDTO) {

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

        try {
            Long userId = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseBodyDTO(userId, "Successful", null));
        }
        catch (DuplicateUserException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseBodyDTO(null, e.getMessage(), e.getErrorCode())); // это 409 код
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBodyDTO(null, e.getMessage(), null)); // это 500 код
        }
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<ResponseBodyDTO> authorize(@RequestBody AuthRequestDTO authRequestDTO) {

        try {
            Long userId = userService.verifyCredentials(authRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseBodyDTO(userId, "Successful", null));
        }
        catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseBodyDTO(null, e.getMessage(), e.getErrorCode())); // это 409 код
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBodyDTO(null, e.getMessage(), null)); // это 500 код
        }
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<ResponseBodyDTO> get_user_data(@PathVariable Long userId) {

        try {
            Map<String, String> userData = userService.getUserDataByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseBodyDTO(userData, "Successful", null));
        }
        catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBodyDTO(null, e.getMessage(), null)); // это 404 код
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBodyDTO(null, e.getMessage(), null)); // это 500 код
        }
    }

    @PutMapping(path = "/{userId}/theme")
    public ResponseEntity<ResponseBodyDTO> change_theme(@PathVariable Long userId, @RequestBody ThemeDTO requestBody) {

        try {
            userService.changeUserTheme(userId, requestBody.getTheme());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseBodyDTO(null, "Successful", null));
        }
        catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBodyDTO(null, e.getMessage(), null)); // это 404 код
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBodyDTO(null, e.getMessage(), null)); // это 500 код
        }
    }
}
