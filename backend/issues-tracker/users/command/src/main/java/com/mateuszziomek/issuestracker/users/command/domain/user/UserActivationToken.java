package com.mateuszziomek.issuestracker.users.command.domain.user;

import java.util.Objects;
import java.util.UUID;

public record UserActivationToken(UUID value) {
    public static UserActivationToken generate() {
        return new UserActivationToken(UUID.randomUUID());
    }

    public UserActivationToken {
        Objects.requireNonNull(value);
    }
}
