package com.mateuszziomek.issuestracker.issues.query.domain.organization;

import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Organization {
    private UUID id;
    private Set<UUID> projectIds;
    private Set<UUID> memberIds;

    public static Organization create(OrganizationCreatedEvent event) {
        return new Organization(
                event.getId(),
                Set.of(),
                Set.of(event.getOrganizationOwnerId())
        );
    }

    public void addProject(OrganizationProjectCreatedEvent event) {
        projectIds.add(event.getProjectId());
    }

    public void joinMember(OrganizationMemberJoinedEvent event) {
        memberIds.add(event.getMemberId());
    }
}
