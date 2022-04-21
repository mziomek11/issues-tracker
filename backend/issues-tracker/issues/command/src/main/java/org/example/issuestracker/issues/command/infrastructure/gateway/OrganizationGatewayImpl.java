package org.example.issuestracker.issues.command.infrastructure.gateway;

import org.example.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import org.example.issuestracker.shared.readmodel.DetailsOrganization;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrganizationGatewayImpl implements OrganizationGateway {
    private final WebClient organizationClient = WebClient.create("http://localhost:8099/api/v1/organization-management");

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationProjectNotFoundException {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     */
    @Override
    public void ensureOrganizationHasProjectAndMember(
            OrganizationId organizationId,
            OrganizationProjectId organizationProjectId,
            OrganizationMemberId organizationMemberId
    ) {
        var organization = findOrganizationByIdOrThrow(organizationId);
        ensureOrganizationHasProject(organization, organizationProjectId);
        ensureOrganizationHasMember(organization, organizationMemberId);
    }

    /**
     * @throws OrganizationNotFoundException {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
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
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
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
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
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
