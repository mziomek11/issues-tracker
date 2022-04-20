package org.example.issuestracker.organizations.command.domain.organization;

import lombok.NoArgsConstructor;
import org.example.cqrs.domain.AggregateRoot;

import static org.example.issuestracker.organizations.command.domain.EventFactory.*;

@NoArgsConstructor
public class Organization extends AggregateRoot {
    private OrganizationId id;
    private OrganizationOwner owner;
    private OrganizationName name;

    public static Organization create(OrganizationId id, OrganizationOwner owner, OrganizationName name) {
        var organization = new Organization();

        organization.raiseEvent(organizationCreated(id, owner.getMemberId(), name));

        return organization;
    }

    @Override
    public OrganizationId getId() {
        return id;
    }
}
