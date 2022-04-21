package org.example.issuestracker.issues.command.application.command.gateway.organization;

import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.IssueCreatorIsNotMemberOfProjectException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.ProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.IssueCreatorId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.project.ProjectId;

public interface OrganizationGateway {
    /**
     * @throws IssueCreatorIsNotMemberOfProjectException if issue creator is not member of project
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws ProjectNotFoundException if project with given id does not exist
     */
    void ensureIssueCreatorIsMemberOfProject(
            OrganizationId organizationId,
            ProjectId projectId,
            IssueCreatorId issueCreatorId
    );
}
