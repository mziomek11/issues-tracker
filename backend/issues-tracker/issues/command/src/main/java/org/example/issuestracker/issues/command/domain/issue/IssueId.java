package org.example.issuestracker.issues.command.domain.issue;

import org.example.cqrs.domain.AggregateId;

import java.util.UUID;

public class IssueId extends AggregateId {
    public IssueId(UUID id) {
        super(id);
    }
}
