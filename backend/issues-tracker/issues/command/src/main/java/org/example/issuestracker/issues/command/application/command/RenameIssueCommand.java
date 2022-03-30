package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;

import java.util.UUID;

public class RenameIssueCommand {
    private final IssueId issueId;
    private final IssueName issueName;

    public RenameIssueCommand(UUID issueId, String issueName) {
        this.issueId = new IssueId(issueId);
        this.issueName = new IssueName(issueName);
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueName getIssueName() {
        return issueName;
    }
}
