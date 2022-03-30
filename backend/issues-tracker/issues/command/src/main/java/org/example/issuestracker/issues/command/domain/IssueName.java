package org.example.issuestracker.issues.command.domain;

import java.util.Objects;

public class IssueName {
    private final String text;

    public IssueName(String text) {
        this.text = Objects.requireNonNull(text);
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
