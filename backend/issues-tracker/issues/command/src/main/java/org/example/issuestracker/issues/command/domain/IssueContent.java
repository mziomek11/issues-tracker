package org.example.issuestracker.issues.command.domain;

import java.util.Objects;

public class IssueContent {
    private final String text;

    public IssueContent(String text) {
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
        if (!(o instanceof IssueContent)) return false;
        IssueContent that = (IssueContent) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
