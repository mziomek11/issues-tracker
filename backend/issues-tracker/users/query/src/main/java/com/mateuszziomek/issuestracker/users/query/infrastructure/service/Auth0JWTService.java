package com.mateuszziomek.issuestracker.users.query.infrastructure.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.JWTService;
import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Auth0JWTService implements JWTService {
    private static final int EXPIRATION_TIME = 60 * 60 * 1000;

    private final Environment env;

    @Override
    public String encode(UUID id) {

        return JWT
                .create()
                .withSubject(id.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(getSecret()));
    }

    /**
     * @throws InvalidJWTException see {@link JWTService#getUserId(String)}
     */
    @Override
    public UUID getUserId(String jwt) {
        DecodedJWT decodedJWT;
        System.out.println(jwt);

        try {
            decodedJWT = JWT.require(Algorithm.HMAC256(getSecret())).build().verify(jwt);
        } catch (JWTVerificationException e) {
            throw new InvalidJWTException();
        }

        return UUID.fromString(decodedJWT.getSubject());
    }

    private String getSecret() {
        var secret = env.getProperty("jwt.secret");

        if (secret == null) {
            throw new RuntimeException("jwt.secret not set");
        }

        return secret;
    }
}
