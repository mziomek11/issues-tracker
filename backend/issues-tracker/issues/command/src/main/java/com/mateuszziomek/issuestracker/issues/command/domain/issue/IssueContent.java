package com.mateuszziomek.issuestracker.issues.command.domain.issue;

import java.util.Objects;

public record IssueContent(String text) {
    public IssueContent {
        Objects.requireNonNull(text);
    }
}
