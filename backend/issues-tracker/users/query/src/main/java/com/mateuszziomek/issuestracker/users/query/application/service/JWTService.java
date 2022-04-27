package com.mateuszziomek.issuestracker.users.query.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JWTService {
    private static final int EXPIRATION_TIME = 3600;

    private final Environment env;

    public String encode(UUID id) {
        var secret = env.getProperty("jwt.secret");

        if (secret == null) {
            throw new RuntimeException("jwt.secret not set");
        }

        return JWT
                .create()
                .withSubject(id.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(secret));
    }
}
