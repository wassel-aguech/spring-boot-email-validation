package com.example.demo.config;


import com.example.demo.Model.User;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok(
                "Inscription reussie ! Verifiez votre email.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String token = userService.verify(user);
        if (token.equals("fail")) {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
        return ResponseEntity.ok(token);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {
        String result = userService.verifyEmail(token);
        return ResponseEntity.ok(result);
    }
}
