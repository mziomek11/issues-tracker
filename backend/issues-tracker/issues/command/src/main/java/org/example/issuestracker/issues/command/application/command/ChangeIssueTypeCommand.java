package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

import java.util.UUID;

public record ChangeIssueTypeCommand(IssueId issueId, IssueType issueType) {
    public ChangeIssueTypeCommand(UUID issueId, IssueType issueType) {
        this(new IssueId(issueId), issueType);
    }
}
