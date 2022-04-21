package org.example.issuestracker.issues.command.domain.organization;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class OrganizationId extends EntityId {
    public OrganizationId(UUID id) {
        super(id);
    }
}
