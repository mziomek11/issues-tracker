package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

public class IssueTypeSetException extends RuntimeException {
    private final IssueId issueId;
    private final IssueType issueType;

    public IssueTypeSetException(IssueId issueId, IssueType issueType) {
        this.issueId = issueId;
        this.issueType = issueType;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueType getIssueType() {
        return issueType;
    }
}
