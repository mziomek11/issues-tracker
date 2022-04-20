package org.example.issuestracker.organizations.command.domain;

import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationName;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;

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

    public static OrganizationMemberInvitedEvent organizationMemberInvited(
            OrganizationId id,
            MemberId memberId
    ) {
        return OrganizationMemberInvitedEvent
                .builder()
                .organizationId(id.getValue())
                .memberId(memberId.getValue())
                .build();
    }

    private EventFactory() {}
}
