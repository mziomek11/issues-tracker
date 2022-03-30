package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

public class IssueTypeChangedEvent extends BaseEvent {
    private final IssueType issueType;

    public IssueTypeChangedEvent(String issueId, IssueType issueType) {
        super(issueId);

        this.issueType = issueType;
    }

    public IssueType getIssueType() {
        return issueType;
    }
}
