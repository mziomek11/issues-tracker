package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public record CloseIssueCommand(IssueId issueId) {
    public CloseIssueCommand(UUID issueId) {
        this(new IssueId(issueId));
    }
}
