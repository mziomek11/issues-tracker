package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.issue.IssueId;

public class IssueClosedException extends RuntimeException {
    private final IssueId issueId;

    public IssueClosedException(IssueId issueId) {
        this.issueId = issueId;
    }

    public IssueId getIssueId() {
        return issueId;
    }
}
