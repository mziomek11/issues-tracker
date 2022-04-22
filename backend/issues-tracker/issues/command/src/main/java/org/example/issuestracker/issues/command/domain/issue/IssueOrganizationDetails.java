package org.example.issuestracker.issues.command.domain.issue;

import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;

import java.util.Objects;
import java.util.UUID;

public record IssueOrganizationDetails(
        OrganizationId organizationId,
        OrganizationProjectId projectId,
        OrganizationMemberId memberId
) {
    public static IssueOrganizationDetails fromUUID(
            UUID organizationId,
            UUID projectId,
            UUID memberId
    ) {
        return new IssueOrganizationDetails(
                new OrganizationId(organizationId),
                new OrganizationProjectId(projectId),
                new OrganizationMemberId(memberId)
        );
    }

    public IssueOrganizationDetails {
        Objects.requireNonNull(organizationId);
        Objects.requireNonNull(projectId);
        Objects.requireNonNull(memberId);
    }
}
