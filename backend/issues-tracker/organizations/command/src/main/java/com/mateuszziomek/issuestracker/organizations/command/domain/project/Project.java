package com.mateuszziomek.issuestracker.organizations.command.domain.project;

import java.util.Objects;

public record Project(ProjectId id, ProjectName name) {
    public Project {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
    }
}
