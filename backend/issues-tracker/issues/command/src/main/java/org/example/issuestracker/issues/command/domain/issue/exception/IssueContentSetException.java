package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

public class IssueContentSetException extends IllegalStateException {
    private final transient IssueId issueId;
    private final transient IssueContent issueContent;

    public IssueContentSetException(IssueId issueId, IssueContent issueContent) {
        this.issueId = issueId;
        this.issueContent = issueContent;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueContent getIssueContent() {
        return issueContent;
    }
}
