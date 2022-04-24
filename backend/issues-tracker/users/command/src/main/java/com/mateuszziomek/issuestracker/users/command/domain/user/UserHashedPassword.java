package com.mateuszziomek.issuestracker.users.command.domain.user;

import java.util.Objects;

public record UserHashedPassword(String text) {
    public UserHashedPassword {
        Objects.requireNonNull(text);
    }
}
