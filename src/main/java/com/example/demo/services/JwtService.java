package com.example.demo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "QHJLJLhAZERCA97MRQFQOAMPIZIZHAARZCSHJQQ88888BSJKQBSUE";

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)  // ✅ UTF_8 fixe
        );
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24)) // ✅ 1000L
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token, Claims::getSubject);
    }

    public boolean validateToken(String token, String username) {
        final String user = extractUsername(token);
        return user.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return parseClaims(token, Claims::getExpiration);
    }

    private <T> T parseClaims(String token, Function<Claims, T> clazz) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return clazz.apply(claims);
    }
}