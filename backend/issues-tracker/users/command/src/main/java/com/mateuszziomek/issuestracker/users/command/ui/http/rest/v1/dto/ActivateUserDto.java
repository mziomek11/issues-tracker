package com.mateuszziomek.issuestracker.users.command.ui.http.rest.v1.dto;

import java.util.UUID;

public record ActivateUserDto(UUID activationToken) {
    public static final String USER_ACTIVATION_TOKEN_FIELD_NAME = "activationToken";
}
