package com.mateuszziomek.issuestracker.users.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.users.query.domain.PasswordVerifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Argon2PasswordVerifier implements PasswordVerifier {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
