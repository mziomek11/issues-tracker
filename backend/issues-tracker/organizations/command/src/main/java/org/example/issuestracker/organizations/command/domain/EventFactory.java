package org.example.issuestracker.organizations.command.domain;

import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationName;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;

public class EventFactory {
    public static OrganizationCreatedEvent organizationCreated(
            OrganizationId id,
            MemberId ownerId,
            OrganizationName name
    ) {
        return OrganizationCreatedEvent
                .builder()
                .organizationId(id.getValue())
                .organizationOwnerId(ownerId.getValue())
                .organizationName(name.text())
                .build();
    }

    private EventFactory() {}
}
