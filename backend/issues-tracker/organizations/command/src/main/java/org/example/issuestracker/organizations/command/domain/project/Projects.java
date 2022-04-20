package org.example.issuestracker.organizations.command.domain.project;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Projects {
    private final Set<Project> projectSet;

    public Projects() {
        this.projectSet = new HashSet<>();
    }

    /**
     * Adds project to projects
     *
     * @param project to be added
     */
    public Projects add(Project project) {
        var newProjectSet = new HashSet<>(projectSet);
        newProjectSet.add(project);

        return new Projects(newProjectSet);
    }
}
