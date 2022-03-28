package org.example.issuestracker.issues.command.domain;

import java.util.Objects;

public class IssueContent {
    private final String text;

    public IssueContent(String text) {
        this.text = Objects.requireNonNull(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
