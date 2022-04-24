package com.mateuszziomek.issuestracker.organizations.command.domain.organization;

import java.util.Objects;

public record OrganizationName(String text) {
    public OrganizationName {
        Objects.requireNonNull(text);
    }
}
