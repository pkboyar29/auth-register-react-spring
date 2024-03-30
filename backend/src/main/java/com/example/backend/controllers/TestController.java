package com.example.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class TestController {
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @GetMapping(path = "/hello-world")
    public Map<String, String> hello_world() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "hello world");
        return map;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "hello-name")
    public Map<String, String> hello_name(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Hello, " + name);
        return map;
    }
}
