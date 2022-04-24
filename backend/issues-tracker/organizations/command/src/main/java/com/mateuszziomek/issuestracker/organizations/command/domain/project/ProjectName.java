package com.mateuszziomek.issuestracker.organizations.command.domain.project;

import java.util.Objects;

public record ProjectName(String text) {
    public ProjectName {
        Objects.requireNonNull(text);
    }
}
