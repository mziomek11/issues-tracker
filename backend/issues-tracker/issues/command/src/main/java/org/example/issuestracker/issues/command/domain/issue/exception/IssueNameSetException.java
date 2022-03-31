package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;

public class IssueNameSetException extends IllegalStateException {
    private final transient IssueId issueId;
    private final transient IssueName issueName;

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
