package com.example.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class TestController {
    @GetMapping(path = "/hello-world")
    public Map<String, String> hello_world() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "hello world");
        return map;
    }
}
