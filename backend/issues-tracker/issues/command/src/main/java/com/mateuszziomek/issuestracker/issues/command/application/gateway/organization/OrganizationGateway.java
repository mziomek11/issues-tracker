package com.mateuszziomek.issuestracker.issues.command.application.gateway.organization;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;

public interface OrganizationGateway {
    /**
     * @throws OrganizationMemberNotFoundException if organization with given id does not exist in organization
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws OrganizationProjectNotFoundException if project with given id does not exist in organization
     */
    void ensureOrganizationHasProjectAndMember(IssueOrganizationDetails organizationDetails);
}
