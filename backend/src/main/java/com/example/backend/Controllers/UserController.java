package com.example.backend.Controllers;

import com.example.backend.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService

    @PostMapping(path = "/register")
    public ResponseEntity register() {

    }
}
