package com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers;

import com.mateuszziomek.cqrs.event.EventModel;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class OrganizationCommandData {
    public static final UUID ORGANIZATION_UUID = UUID.randomUUID();
    public static final UUID OWNER_UUID = UUID.randomUUID();
    public static final UUID PROJECT_UUID = UUID.randomUUID();
    public static final UUID INVITED_MEMBER_UUID = UUID.randomUUID();
    public static final String INVITED_MEMBER_EMAIL = "example@mail.com";
    public static final OrganizationId ORGANIZATION_ID = new OrganizationId(ORGANIZATION_UUID);

    public static final CreateOrganizationCommand CREATE_ORGANIZATION_COMMAND = CreateOrganizationCommand
            .builder()
            .organizationId(ORGANIZATION_UUID)
            .organizationName("Example name")
            .organizationOwnerId(OWNER_UUID)
            .build();

    public static final CreateOrganizationProjectCommand CREATE_ORGANIZATION_PROJECT_COMMAND = CreateOrganizationProjectCommand
            .builder()
            .organizationId(ORGANIZATION_UUID)
            .organizationOwnerId(OWNER_UUID)
            .projectId(PROJECT_UUID)
            .projectName("Example name")
            .build();

    public static final InviteOrganizationMemberCommand INVITE_ORGANIZATION_MEMBER_COMMAND = InviteOrganizationMemberCommand
            .builder()
            .organizationId(ORGANIZATION_UUID)
            .organizationOwnerId(OWNER_UUID)
            .memberEmail(INVITED_MEMBER_EMAIL)
            .build();

    public static final JoinOrganizationMemberCommand JOIN_ORGANIZATION_MEMBER_COMMAND = JoinOrganizationMemberCommand
            .builder()
            .organizationId(ORGANIZATION_UUID)
            .memberId(INVITED_MEMBER_UUID)
            .build();
    public static final OrganizationCreatedEvent ORGANIZATION_CREATED_EVENT = OrganizationCreatedEvent
            .builder()
            .organizationId(ORGANIZATION_UUID)
            .organizationName(CREATE_ORGANIZATION_COMMAND.getOrganizationName().text())
            .organizationOwnerId(OWNER_UUID)
            .build();

    public final static EventModel ORGANIZATION_CREATED_EVENT_MODEL = new EventModel(
            ORGANIZATION_UUID,
            Date.from(Instant.now()),
            ORGANIZATION_ID,
            OrganizationCreatedEvent.class.getTypeName(),
            0,
            "OrganizationCreatedEvent",
            ORGANIZATION_CREATED_EVENT
    );

    public static final OrganizationMemberInvitedEvent ORGANIZATION_MEMBER_INVITED_EVENT = OrganizationMemberInvitedEvent
            .builder()
            .organizationId(ORGANIZATION_UUID)
            .memberId(INVITED_MEMBER_UUID)
            .build();

    public final static EventModel ORGANIZATION_MEMBER_INVITED_EVENT_MODEL = new EventModel(
            ORGANIZATION_UUID,
            Date.from(Instant.now()),
            ORGANIZATION_ID,
            OrganizationMemberInvitedEvent.class.getTypeName(),
            0,
            "OrganizationMemberInvitedEvent",
            ORGANIZATION_MEMBER_INVITED_EVENT
    );

    private OrganizationCommandData() {}
}
