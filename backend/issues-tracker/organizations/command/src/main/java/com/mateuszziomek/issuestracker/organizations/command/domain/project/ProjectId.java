package com.mateuszziomek.issuestracker.organizations.command.domain.project;

import com.mateuszziomek.cqrs.domain.EntityId;

import java.util.UUID;

public class ProjectId extends EntityId {
    public ProjectId(UUID id) {
        super(id);
    }
}
