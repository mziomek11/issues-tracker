package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;

import java.util.UUID;

public record RenameIssueCommand(IssueId issueId, IssueName issueName) {
    public RenameIssueCommand(UUID issueId, String issueName) {
        this(new IssueId(issueId), new IssueName(issueName));
    }
}
