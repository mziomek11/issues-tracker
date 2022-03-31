package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;

public class IssueNameSetException extends RuntimeException {
    private final IssueId issueId;
    private final IssueName issueName;

    public IssueNameSetException(IssueId issueId, IssueName issueName) {
        this.issueId = issueId;
        this.issueName = issueName;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueName issueName() {
        return issueName;
    }
}
