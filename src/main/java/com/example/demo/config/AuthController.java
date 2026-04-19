package com.example.demo.config;


import com.example.demo.Model.User;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            userService.register(request);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Inscription reussie ! Verifiez votre email.");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String token = userService.verify(user);

        if (token.equals("fail")) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Email ou mot de passe incorrect"));
        }

        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {
        String result = userService.verifyEmail(token);
        return ResponseEntity.ok(result);
    }
}
