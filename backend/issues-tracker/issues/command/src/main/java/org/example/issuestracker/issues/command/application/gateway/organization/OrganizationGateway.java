package org.example.issuestracker.issues.command.application.gateway.organization;

import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;

public interface OrganizationGateway {
    /**
     * @throws OrganizationMemberNotFoundException if organization with given id does not exist in organization
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws OrganizationProjectNotFoundException if project with given id does not exist in organization
     */
    void ensureOrganizationHasProjectAndMember(IssueOrganizationDetails organizationDetails);
}
