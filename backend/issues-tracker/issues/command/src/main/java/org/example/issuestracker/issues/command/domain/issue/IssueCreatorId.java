package org.example.issuestracker.issues.command.domain.issue;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class IssueCreatorId extends EntityId {
    public IssueCreatorId(UUID id) {
        super(id);
    }
}
