package com.mateuszziomek.issuestracker.organizations.command.domain;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationName;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.ProjectId;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.ProjectName;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;

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
