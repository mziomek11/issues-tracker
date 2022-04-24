package com.mateuszziomek.issuestracker.organizations.command.domain.project;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Projects {
    private final Set<Project> projectSet;

    public Projects() {
        this.projectSet = new HashSet<>();
    }

    public Projects add(Project project) {
        var newProjectSet = new HashSet<>(projectSet);
        newProjectSet.add(project);

        return new Projects(newProjectSet);
    }
}
