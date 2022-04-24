package com.mateuszziomek.issuestracker.issues.command.domain.organization;

import com.mateuszziomek.cqrs.domain.EntityId;

import java.util.UUID;

public class OrganizationMemberId extends EntityId {
    public OrganizationMemberId(UUID id) {
        super(id);
    }
}
