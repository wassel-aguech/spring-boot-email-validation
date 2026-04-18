package com.example.demo.services;

import com.example.demo.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(User user, String token) {
        String confirmUrl = baseUrl + "/api/auth/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Confirmation de votre adresse email");
        message.setText(
                "Bonjour " + user.getFirstName() + ",\n\n"
                        + "Cliquez sur le lien pour activer votre compte :\n\n"
                        + confirmUrl + "\n\n"
                        + "Ce lien expire dans 24 heures.\n\n"
                        + "Cordialement."
        );

        mailSender.send(message);
    }
}