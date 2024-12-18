package com.project.midtrans2.registration.controller;

import com.project.midtrans2.registration.dto.RegistrationRequest;
import com.project.midtrans2.registration.model.Registration;
import com.project.midtrans2.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        logger.info("Received registration request for email: {}", request.getEmail());

        String result = registrationService.register(request);

        // Mencatat hasil pendaftaran
        if (result.contains("successful")) {
            logger.info("Registration successful for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(result); // Mengembalikan status 201
        } else {
            logger.warn("Registration failed for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // Mengembalikan status 400
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token) {
        logger.info("Received activation request with token: {}", token);

        String result = registrationService.verifyAccount(token);
        if (result.equals("Account activated successfully!")) {
            return ResponseEntity.ok(result);
        } else if (result.equals("Token expired!")) {
            return ResponseEntity.status(HttpStatus.GONE).body(result); // 410 GONE
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // 400 BAD REQUEST
        }
    }
}