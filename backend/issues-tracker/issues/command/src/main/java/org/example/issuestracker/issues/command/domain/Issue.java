package org.example.issuestracker.issues.command.domain;

import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.issues.common.domain.IssueStatus;
import org.example.issuestracker.issues.common.domain.IssueType;
import org.example.issuestracker.issues.common.event.IssueOpenedEvent;

public class Issue extends AggregateRoot {
    private IssueId issueId;
    private IssueType issueType;
    private IssueStatus issueStatus;
    private IssueContent issueContent;

    public Issue(IssueId issueId, IssueType issueType, IssueContent issueContent) {
        raiseEvent(new IssueOpenedEvent(
                issueId.toString(),
                issueType,
                issueContent.toString()
        ));
    }

    public void on(IssueOpenedEvent issueOpenedEvent) {
        this.issueId = IssueId.fromString(issueOpenedEvent.getId());
        this.issueType = issueOpenedEvent.getIssueType();
        this.issueStatus = IssueStatus.OPENED;
        this.issueContent = new IssueContent(issueOpenedEvent.getIssueContent());
    }

    @Override
    public IssueId getId() {
        return issueId;
    }
}
