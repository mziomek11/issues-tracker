package org.example.issuestracker.issues.command.infrastructure.gateway;

import org.example.issuestracker.issues.command.application.command.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.IssueCreatorIsNotMemberOfProjectException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.ProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.IssueCreatorId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.project.ProjectId;
import org.example.issuestracker.shared.readmodel.DetailsOrganization;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OrganizationGatewayImpl implements OrganizationGateway {
    private final WebClient organizationClient = WebClient.create("http://localhost:8099/api/v1/organization-management");

    /**
     * @throws IssueCreatorIsNotMemberOfProjectException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     * @throws ProjectNotFoundException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     */
    @Override
    public void ensureIssueCreatorIsMemberOfProject(
            OrganizationId organizationId,
            ProjectId projectId,
            IssueCreatorId issueCreatorId
    ) {
        var organization = findOrganizationByIdOrThrow(organizationId);
        ensureOrganizationHasProject(organization, projectId);

        if (!organization.getOwnerId().equals(issueCreatorId.getValue())) {
            throw new IssueCreatorIsNotMemberOfProjectException(projectId, issueCreatorId);
        }
    }

    /**
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     */
    private DetailsOrganization findOrganizationByIdOrThrow(OrganizationId organizationId) {
        var organization = organizationClient
                .get()
                .uri("/organizations/{organizationId}", organizationId.getValue())
                .retrieve()
                .onStatus(httpStatus -> httpStatus.equals(HttpStatus.NOT_FOUND), response -> {
                    throw new OrganizationNotFoundException(organizationId);
                })
                .bodyToMono(DetailsOrganization.class)
                .block();

        if (organization == null) {
            throw new OrganizationNotFoundException(organizationId);
        }

        return organization;
    }

    /**
     * @throws ProjectNotFoundException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     */
    private void ensureOrganizationHasProject(DetailsOrganization organization, ProjectId projectId) {
        organization
                .getProjects()
                .stream()
                .map(DetailsOrganization.Project::getId)
                .filter(projectId.getValue()::equals)
                .findAny()
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }
}
