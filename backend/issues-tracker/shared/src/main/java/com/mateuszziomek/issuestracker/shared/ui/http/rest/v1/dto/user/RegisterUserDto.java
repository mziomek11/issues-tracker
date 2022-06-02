package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.user;

public record RegisterUserDto(String email, String password) {
    public static final String USER_EMAIL_FIELD_NAME = "email";
    public static final String USER_PLAIN_PASSWORD_FIELD_NAME = "password";
}
