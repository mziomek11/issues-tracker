package org.example.issuestracker.issues.command.domain;

import org.example.cqrs.domain.AggregateId;

import java.util.UUID;

public class IssueId extends AggregateId {
    public static IssueId fromString(String value) {
        return new IssueId(UUID.fromString(value));
    }

    public IssueId(UUID id) {
        super(id);
    }
}
