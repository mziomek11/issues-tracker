package org.example.issuestracker.organizations.command.domain;

import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationId;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationName;
import org.example.issuestracker.organizations.command.domain.project.ProjectId;
import org.example.issuestracker.organizations.command.domain.project.ProjectName;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;

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

    public static OrganizationMemberJoinedEvent organizationMemberJoined(
            OrganizationId id,
            MemberId memberId
    ) {
        return OrganizationMemberJoinedEvent
                .builder()
                .organizationId(id.getValue())
                .memberId(memberId.getValue())
                .build();
    }

    public static OrganizationProjectCreatedEvent organizationProjectCreated(
            OrganizationId organizationId,
            ProjectId projectId,
            ProjectName projectName
    ) {
        return OrganizationProjectCreatedEvent
                .builder()
                .organizationId(organizationId.getValue())
                .projectId(projectId.getValue())
                .projectName(projectName.text())
                .build();
    }

    private EventFactory() {}
}
