package com.example.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/captcha")
public class CaptchaController {
    final private String secretKey;
    final private String verifyUrl;

    public CaptchaController(@Value("${recaptcha.secret-key}") String secretKey, @Value("${recaptcha.verify-url}") String verifyUrl) {
        this.secretKey = secretKey;
        this.verifyUrl = verifyUrl;
    }
    @PostMapping(path = "/verify-token")
    public ResponseEntity<Map<String, String>> verify_token(@RequestBody Map<String, String> requestBody) throws JsonProcessingException {

        String token = requestBody.get("token");

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("secret", secretKey);
        data.add("response", token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(verifyUrl, request, JsonNode.class);
        JsonNode responseBody = response.getBody();

        Map<String, String> map = new HashMap<>();

        if (Objects.equals(responseBody.get("success").asText(), "true")) {
            map.put("message", "Token is valid");
            return ResponseEntity.status(HttpStatus.OK).body(map);
        }
        else {
            map.put("message", "Token isn't valid");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }
}
