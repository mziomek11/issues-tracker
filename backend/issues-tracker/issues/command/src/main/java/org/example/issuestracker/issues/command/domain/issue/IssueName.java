package org.example.issuestracker.issues.command.domain.issue;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueName)) return false;
        IssueName issueName = (IssueName) o;
        return Objects.equals(text, issueName.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
