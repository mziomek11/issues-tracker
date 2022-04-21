package org.example.issuestracker.issues.command.domain.organization;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class OrganizationMemberId extends EntityId {
    public OrganizationMemberId(UUID id) {
        super(id);
    }
}
