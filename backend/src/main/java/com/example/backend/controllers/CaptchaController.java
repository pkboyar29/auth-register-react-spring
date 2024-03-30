package com.example.backend.controllers;

import com.example.backend.services.CaptchaService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/captcha")
public class CaptchaController {
    final private CaptchaService captchaService;
    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
    @PostMapping(path = "/verify-token")
    public ResponseEntity<Map<String, String>> verify_token(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        JsonNode responseFromExternalAPI = captchaService.verifyRecaptchaToken(token);

        Map<String, String> response = new HashMap<>();
        if (responseFromExternalAPI.has("success")) {
            if (Objects.equals(responseFromExternalAPI.get("success").asText(), "true")) {
                response.put("message", "Token is valid");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.put("message", "Token isn't valid");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } else {
            response.put("message", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
