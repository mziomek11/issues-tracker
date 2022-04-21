package org.example.issuestracker.issues.command.domain.issue;

import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;

import java.util.UUID;

public class IssueCreatorId extends OrganizationMemberId {
    public IssueCreatorId(UUID id) {
        super(id);
    }
}
