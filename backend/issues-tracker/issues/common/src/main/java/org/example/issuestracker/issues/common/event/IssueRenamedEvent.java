package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;

import java.util.Objects;

public class IssueRenamedEvent extends BaseEvent {
    private final String issueName;

    public IssueRenamedEvent(String id, String issueName) {
        super(id);

        this.issueName = Objects.requireNonNull(issueName);
    }

    public String getIssueName() {
        return issueName;
    }
}
