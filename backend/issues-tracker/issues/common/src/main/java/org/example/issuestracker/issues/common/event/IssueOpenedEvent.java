package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;
import org.example.issuestracker.issues.common.domain.IssueType;

import java.util.Objects;

public class IssueOpenedEvent extends BaseEvent {
    private final IssueType issueType;
    private final String issueContent;

    public IssueOpenedEvent(String id, IssueType issueType, String issueContent) {
        super(id);

        this.issueType = issueType;
        this.issueContent = Objects.requireNonNull(issueContent);
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public String getIssueContent() {
        return issueContent;
    }
}
