package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;

import java.util.Objects;

public class IssueContentChangedEvent extends BaseEvent {
    private final String issueContent;

    public IssueContentChangedEvent(String issueId, String issueContent) {
        super(issueId);

        this.issueContent = Objects.requireNonNull(issueContent);
    }

    public String getIssueContent() {
        return issueContent;
    }
}
