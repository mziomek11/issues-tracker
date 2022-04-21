package org.example.issuestracker.issues.command.application.command.gateway.organization.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.issue.IssueCreatorId;
import org.example.issuestracker.issues.command.domain.project.ProjectId;

@RequiredArgsConstructor
@Getter
public class IssueCreatorIsNotMemberOfProjectException extends IllegalStateException {
    private final transient ProjectId projectId;
    private final transient IssueCreatorId issueCreatorId;
}
