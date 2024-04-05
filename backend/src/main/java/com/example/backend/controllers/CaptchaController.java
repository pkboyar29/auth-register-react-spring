package com.example.backend.controllers;

import com.example.backend.dto.ResponseBodyDTO;
import com.example.backend.services.CaptchaService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
    @PostMapping(path = "/verify-token")
    public ResponseEntity<ResponseBodyDTO> verify_token(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        JsonNode responseFromExternalAPI = captchaService.verifyRecaptchaToken(token);

        if (responseFromExternalAPI.has("success")) {
            if (Objects.equals(responseFromExternalAPI.get("success").asText(), "true")) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseBodyDTO(null, "Token is valid", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBodyDTO(null, "Token isn't valid", null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBodyDTO(null, "Internal server error", null));
        }
    }
}
