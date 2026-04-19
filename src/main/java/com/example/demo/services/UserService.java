package com.example.demo.services;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public void register(RegisterRequest request) {
        // 1. Verifier si l'email existe deja
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email deja enregistre");
            // ✅ 409 CONFLICT au lieu de RuntimeException
        }
        // 2. Generer le token
        String token = UUID.randomUUID().toString();
        // 3. Creer l'utilisateur avec le token
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.EMPLOYEE)
                .enabled(false)
                .verificationToken(token)
                .tokenExpiry(LocalDateTime.now().plusHours(24))
                .build();
        userRepository.save(user);
        // 4. Envoyer l'email
        emailService.sendVerificationEmail(user, token);
    }


    public String verifyEmail(String token) {
        // 1. Chercher l'utilisateur par son token
        User user = userRepository
                .findByVerificationToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Token invalide"));
        // 2. Verifier si le token a expire
        if (LocalDateTime.now().isAfter(user.getTokenExpiry())) {
            throw new RuntimeException("Token expire");
        }
        // 3. Activer le compte et supprimer le token
        user.setEnabled(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);
        return "Compte active avec succes !";
    }


    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        }
        return "fail";
    }

}

