package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.user;

import java.util.UUID;

public record ActivateUserDto(UUID activationToken) {
    public static final String USER_ACTIVATION_TOKEN_FIELD_NAME = "activationToken";
}
