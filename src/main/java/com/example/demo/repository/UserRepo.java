package com.example.demo.repository;

import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);        // ✅ Optional

    Optional<User> findByVerificationToken(String token);
    boolean existsByEmail(String email);

}

