package org.example.issuestracker.issues.command.application.exception;

import org.example.issuestracker.issues.command.domain.IssueId;

public class IssueNotFoundException extends RuntimeException {
    private IssueId issueId;

    public IssueNotFoundException(IssueId issueId) {
        this.issueId = issueId;
    }

    public IssueId getIssueId() {
        return issueId;
    }
}
