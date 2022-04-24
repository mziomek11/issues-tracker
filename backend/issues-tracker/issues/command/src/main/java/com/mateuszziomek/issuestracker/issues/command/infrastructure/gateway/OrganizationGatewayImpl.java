package com.mateuszziomek.issuestracker.issues.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.shared.readmodel.DetailsOrganization;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrganizationGatewayImpl implements OrganizationGateway {
    private final WebClient organizationClient = WebClient.create("http://localhost:8099/api/v1/organization-management");

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void ensureOrganizationHasProjectAndMember(IssueOrganizationDetails organizationDetails) {
        var organization = findOrganizationByIdOrThrow(organizationDetails.organizationId());
        ensureOrganizationHasProject(organization, organizationDetails.projectId());
        ensureOrganizationHasMember(organization, organizationDetails.memberId());
    }

    /**
     * @throws OrganizationNotFoundException {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
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
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    private void ensureOrganizationHasProject(DetailsOrganization organization, OrganizationProjectId organizationProjectId) {
        organization
                .getProjects()
                .stream()
                .map(DetailsOrganization.Project::getId)
                .filter(organizationProjectId.getValue()::equals)
                .findAny()
                .orElseThrow(() -> new OrganizationProjectNotFoundException(organizationProjectId));
    }

    /**
     * @throws OrganizationMemberNotFoundException {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    private void ensureOrganizationHasMember(DetailsOrganization organization, OrganizationMemberId organizationMemberId) {
        organization
                .getMembers()
                .stream()
                .map(DetailsOrganization.Member::getId)
                .filter(organizationMemberId::equals)
                .findAny()
                .orElseThrow(() -> new OrganizationMemberNotFoundException(organizationMemberId));
    }
}
