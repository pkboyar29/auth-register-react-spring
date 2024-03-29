package com.example.backend.controllers;

import com.example.backend.services.CaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/captcha")
public class CaptchaController {

//    private final CaptchaService captchaService;
//
//    @PostMapping(path = "/verify-token")
//    public ResponseEntity verify_token() {
//        return ResponseEntity.ok();
//    }
}
