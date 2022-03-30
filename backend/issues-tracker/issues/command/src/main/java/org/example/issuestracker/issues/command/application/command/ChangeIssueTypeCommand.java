package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.IssueId;
import org.example.issuestracker.issues.common.domain.IssueType;

import java.util.UUID;

public class ChangeIssueTypeCommand {
    private final IssueId issueId;
    private final IssueType issueType;

    public ChangeIssueTypeCommand(UUID issueId, IssueType issueType) {
        this.issueId = new IssueId(issueId);
        this.issueType = issueType;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueType getIssueType() {
        return issueType;
    }
}
