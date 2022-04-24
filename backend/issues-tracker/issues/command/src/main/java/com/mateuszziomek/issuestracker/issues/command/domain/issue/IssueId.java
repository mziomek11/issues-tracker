package com.mateuszziomek.issuestracker.issues.command.domain.issue;

import com.mateuszziomek.cqrs.domain.AggregateId;

import java.util.UUID;

public class IssueId extends AggregateId {
    public IssueId(UUID id) {
        super(id);
    }
}
