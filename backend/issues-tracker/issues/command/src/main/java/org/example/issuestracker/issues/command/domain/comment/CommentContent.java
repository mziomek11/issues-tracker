package org.example.issuestracker.issues.command.domain.comment;

import java.util.Objects;

public record CommentContent(String text) {
    public CommentContent {
        Objects.requireNonNull(text);
    }
}
