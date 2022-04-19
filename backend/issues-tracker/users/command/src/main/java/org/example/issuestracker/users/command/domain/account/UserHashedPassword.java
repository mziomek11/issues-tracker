package org.example.issuestracker.users.command.domain.account;

import java.util.Objects;

public record UserHashedPassword(String text) {
    public UserHashedPassword {
        Objects.requireNonNull(text);
    }
}
