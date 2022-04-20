package org.example.issuestracker.users.command.domain.user;

import java.util.Objects;

public record UserPlainPassword(String text) {
    public UserPlainPassword {
        Objects.requireNonNull(text);
    }
}
