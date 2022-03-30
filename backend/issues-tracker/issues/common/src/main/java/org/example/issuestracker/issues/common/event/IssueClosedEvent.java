package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;

public class IssueClosedEvent extends BaseEvent {
    public IssueClosedEvent(String issueId) {
        super(issueId);
    }
}
