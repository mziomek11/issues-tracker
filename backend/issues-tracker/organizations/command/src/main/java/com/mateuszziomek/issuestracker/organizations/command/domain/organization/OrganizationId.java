package com.mateuszziomek.issuestracker.organizations.command.domain.organization;

import com.mateuszziomek.cqrs.domain.AggregateId;

import java.util.UUID;

public class OrganizationId extends AggregateId {
    public OrganizationId(UUID id) {
        super(id);
    }
}
