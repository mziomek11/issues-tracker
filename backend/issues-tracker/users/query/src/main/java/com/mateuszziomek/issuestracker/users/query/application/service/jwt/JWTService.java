package com.mateuszziomek.issuestracker.users.query.application.service.jwt;

import com.mateuszziomek.issuestracker.users.query.application.service.jwt.exception.InvalidJWTException;

import java.util.UUID;

public interface JWTService {
    String encode(UUID id);
    /**
     * @throws InvalidJWTException if jwt is no valid
     */
    UUID getUserId(String jwt);
}
