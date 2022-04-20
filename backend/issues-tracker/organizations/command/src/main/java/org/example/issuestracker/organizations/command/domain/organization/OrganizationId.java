package org.example.issuestracker.organizations.command.domain.organization;

import org.example.cqrs.domain.AggregateId;

import java.util.UUID;

public class OrganizationId extends AggregateId {
    public OrganizationId(UUID id) {
        super(id);
    }
}
