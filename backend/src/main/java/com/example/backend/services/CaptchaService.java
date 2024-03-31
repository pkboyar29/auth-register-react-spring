package com.example.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {
    final private String secretKey;
    final private String verifyUrl;
    public CaptchaService(@Value("${recaptcha.secret-key}") String secretKey, @Value("${recaptcha.verify-url}") String verifyUrl) {
        this.secretKey = secretKey;
        this.verifyUrl = verifyUrl;
    }
    public JsonNode verifyRecaptchaToken(String token) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("secret", secretKey);
        data.add("response", token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(verifyUrl, request, JsonNode.class);

        return response.getBody();
    }
}
