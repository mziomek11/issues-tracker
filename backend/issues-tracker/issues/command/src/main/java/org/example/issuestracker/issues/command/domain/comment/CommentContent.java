package org.example.issuestracker.issues.command.domain.comment;

import java.util.Objects;

public class CommentContent {
    private final String text;

    public CommentContent(String text) {
        this.text = Objects.requireNonNull(text);
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentContent)) return false;
        CommentContent that = (CommentContent) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
