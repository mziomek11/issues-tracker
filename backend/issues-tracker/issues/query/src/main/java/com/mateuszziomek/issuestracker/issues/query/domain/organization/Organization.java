package com.mateuszziomek.issuestracker.issues.query.domain.organization;

import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Organization {
    private UUID id;
    private Set<UUID> memberIds;

    public static Organization create(OrganizationCreatedEvent event) {
        return new Organization(
                event.getId(),
                Set.of(event.getOrganizationOwnerId())
        );
    }

    public void joinMember(OrganizationMemberJoinedEvent event) {
        memberIds.add(event.getMemberId());
    }
}
