package org.example.issuestracker.organizations.command.domain.organization;

import lombok.NoArgsConstructor;
import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;

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

    public void on(OrganizationCreatedEvent organizationCreatedEvent) {
        id = new OrganizationId(organizationCreatedEvent.getId());
        owner = new OrganizationOwner(new MemberId(organizationCreatedEvent.getOrganizationOwnerId()));
        name = new OrganizationName(organizationCreatedEvent.getOrganizationName());
    }
}
