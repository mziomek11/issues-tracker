package com.mateuszziomek.issuestracker.issues.command.domain.organization;

import com.mateuszziomek.cqrs.domain.EntityId;

import java.util.UUID;

public class OrganizationProjectId extends EntityId {
    public OrganizationProjectId(UUID id) {
        super(id);
    }
}
