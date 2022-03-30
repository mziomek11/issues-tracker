package org.example.issuestracker.issues.command.domain;

import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.issues.common.domain.IssueStatus;
import org.example.issuestracker.issues.common.domain.IssueType;
import org.example.issuestracker.issues.common.event.IssueClosedEvent;
import org.example.issuestracker.issues.common.event.IssueOpenedEvent;
import org.example.issuestracker.issues.common.event.IssueRenamedEvent;
import org.example.issuestracker.issues.common.event.IssueTypeChangedEvent;

public class Issue extends AggregateRoot {
    private IssueId issueId;
    private IssueType issueType;
    private IssueStatus issueStatus;
    private IssueContent issueContent;
    private IssueName issueName;

    public static Issue open(IssueId issueId, IssueType issueType, IssueContent issueContent, IssueName issueName) {
        var issue = new Issue();

        issue.raiseEvent(new IssueOpenedEvent(
                issueId.toString(),
                issueType,
                issueContent.getText(),
                issueName.getText()
        ));

        return issue;
    }

    public Issue() {}

    @Override
    public IssueId getId() {
        return issueId;
    }

    private boolean isClosed() {
        return issueStatus.equals(IssueStatus.CLOSED);
    }

    public void close() {
        if (isClosed()) {
            throw new IssueClosedException();
        }

        raiseEvent(new IssueClosedEvent(issueId.toString()));
    }

    public void rename(IssueName newIssueName) {
        if (isClosed()) {
            throw new IssueClosedException();
        }

        raiseEvent(new IssueRenamedEvent(issueId.toString(), newIssueName.getText()));
    }

    public void changeType(IssueType newIssueType) {
        if (isClosed()) {
            throw new IssueClosedException();
        }

        if (issueType.equals(newIssueType)) {
            throw new IssueTypeAlreadySetException();
        }

        raiseEvent(new IssueTypeChangedEvent(issueId.toString(), newIssueType));
    }

    public void on(IssueOpenedEvent issueOpenedEvent) {
        this.issueId = IssueId.fromString(issueOpenedEvent.getId());
        this.issueType = issueOpenedEvent.getIssueType();
        this.issueStatus = IssueStatus.OPENED;
        this.issueContent = new IssueContent(issueOpenedEvent.getIssueContent());
        this.issueName = new IssueName(issueOpenedEvent.getIssueName());
    }

    public void on(IssueClosedEvent issueClosedEvent) {
        this.issueStatus = IssueStatus.CLOSED;
    }

    public void on(IssueRenamedEvent issueRenamedEvent) {
        this.issueName = new IssueName(issueRenamedEvent.getIssueName());
    }

    public void on(IssueTypeChangedEvent issueTypeChangedEvent) {
        this.issueType = issueTypeChangedEvent.getIssueType();
    }
}
