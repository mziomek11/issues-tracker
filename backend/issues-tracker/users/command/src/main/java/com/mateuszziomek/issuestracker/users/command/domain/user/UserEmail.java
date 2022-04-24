package com.mateuszziomek.issuestracker.users.command.domain.user;

import java.util.Objects;

public record UserEmail(String text) {
    public UserEmail {
        Objects.requireNonNull(text);
    }
}
