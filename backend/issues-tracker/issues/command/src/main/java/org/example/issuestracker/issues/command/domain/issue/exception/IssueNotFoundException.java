package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.issue.IssueId;

public class IssueNotFoundException extends IllegalStateException {
    private IssueId issueId;

    public IssueNotFoundException(IssueId issueId) {
        this.issueId = issueId;
    }

    public IssueId getIssueId() {
        return issueId;
    }
}
