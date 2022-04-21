package org.example.issuestracker.issues.command.domain.organization;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class OrganizationProjectId extends EntityId {
    public OrganizationProjectId(UUID id) {
        super(id);
    }
}
