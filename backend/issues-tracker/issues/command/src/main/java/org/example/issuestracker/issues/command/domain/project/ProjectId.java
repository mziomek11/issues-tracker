package org.example.issuestracker.issues.command.domain.project;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class ProjectId extends EntityId {
    public ProjectId(UUID id) {
        super(id);
    }
}
