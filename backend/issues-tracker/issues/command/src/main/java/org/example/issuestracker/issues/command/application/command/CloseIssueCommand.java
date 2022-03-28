package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.IssueId;

import java.util.UUID;

public class CloseIssueCommand {
    private final IssueId issueId;

    public CloseIssueCommand(UUID issueId) {
        this.issueId = new IssueId(issueId);
    }

    public IssueId getIssueId() {
        return issueId;
    }
}
