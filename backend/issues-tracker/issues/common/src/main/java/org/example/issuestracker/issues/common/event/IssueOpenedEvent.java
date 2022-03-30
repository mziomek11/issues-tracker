package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;
import org.example.issuestracker.issues.common.domain.IssueType;

import java.util.Objects;

public class IssueOpenedEvent extends BaseEvent {
    private final IssueType issueType;
    private final String issueContent;
    private final String issueName;

    public IssueOpenedEvent(String issueId, IssueType issueType, String issueContent, String issueName) {
        super(issueId);

        this.issueType = issueType;
        this.issueContent = Objects.requireNonNull(issueContent);
        this.issueName = Objects.requireNonNull(issueName);
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public String getIssueContent() {
        return issueContent;
    }

    public String getIssueName() {
        return issueName;
    }
}
