package com.mateuszziomek.issuestracker.organizations.query.infrastructure.gateway.notification;

import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;

import java.util.Map;

public class OrganizationNotification {
    private static final String MEMBER_ID = "memberId";
    private static final String ORGANIZATION_ID = "organizationId";
    private static final String PROJECT_ID = "projectId";

    private OrganizationNotification() {}

    public static Map<String, ? extends Object> created(OrganizationCreatedEvent event) {
        return Map.of(
                ORGANIZATION_ID,
                event.getId()
        );
    }

    public static Map<String, ? extends Object> memberInvited(OrganizationMemberInvitedEvent event) {
        return Map.of(
                ORGANIZATION_ID,
                event.getId(),
                MEMBER_ID,
                event.getMemberId()
        );
    }

    public static Map<String, ? extends Object> memberJoined(OrganizationMemberJoinedEvent event) {
        return Map.of(
                ORGANIZATION_ID,
                event.getId(),
                MEMBER_ID,
                event.getMemberId()
        );
    }

    public static Map<String, ? extends Object> projectCreated(OrganizationProjectCreatedEvent event) {
        return Map.of(
                ORGANIZATION_ID,
                event.getId(),
                PROJECT_ID,
                event.getProjectId()
        );
    }
}
