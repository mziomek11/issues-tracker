package com.mateuszziomek.issuestracker.issues.command.projection;

import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Organization {
    private UUID id;
    private Set<UUID> projectIds;
    private Set<UUID> memberIds;

    public static Organization create(OrganizationCreatedEvent event) {
        var memberIds = new HashSet<UUID>();
        memberIds.add(event.getOrganizationOwnerId());
        return new Organization(
                event.getId(),
                new HashSet<>(),
                memberIds
        );
    }

    public void addProject(OrganizationProjectCreatedEvent event) {
        projectIds.add(event.getProjectId());
    }

    public void joinMember(OrganizationMemberJoinedEvent event) {
        memberIds.add(event.getMemberId());
    }
}