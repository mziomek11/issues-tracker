package com.mateuszziomek.issuestracker.users.query.domain;

public interface PasswordVerifier {
    boolean matches(String plainPassword, String hashedPassword);
}
