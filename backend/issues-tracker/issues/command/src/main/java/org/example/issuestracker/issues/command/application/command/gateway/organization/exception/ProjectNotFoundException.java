package org.example.issuestracker.issues.command.application.command.gateway.organization.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.project.ProjectId;

@RequiredArgsConstructor
@Getter
public class ProjectNotFoundException extends IllegalStateException {
    private final transient ProjectId projectId;
}
