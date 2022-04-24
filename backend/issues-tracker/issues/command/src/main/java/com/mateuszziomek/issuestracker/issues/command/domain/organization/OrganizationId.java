package com.mateuszziomek.issuestracker.issues.command.domain.organization;

import com.mateuszziomek.cqrs.domain.EntityId;

import java.util.UUID;

public class OrganizationId extends EntityId {
    public OrganizationId(UUID id) {
        super(id);
    }
}
