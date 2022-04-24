package com.mateuszziomek.issuestracker.issues.command.domain.issue;

import java.util.Objects;

public record IssueName(String text) {
    public IssueName {
        Objects.requireNonNull(text);
    }
}
