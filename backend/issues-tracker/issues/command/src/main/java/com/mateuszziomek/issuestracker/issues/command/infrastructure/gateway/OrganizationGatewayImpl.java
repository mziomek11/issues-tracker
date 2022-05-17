package com.mateuszziomek.issuestracker.issues.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class OrganizationGatewayImpl implements OrganizationGateway {
    private final DiscoveryClient discoveryClient;

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationServiceUnavailableException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
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
        var organization = organizationClient()
                .get()
                .uri("/organizations/{organizationId}", organizationId.getValue())
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.SYSTEM.toString())
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
                .filter(organizationMemberId.getValue()::equals)
                .findFirst()
                .orElseThrow(() -> new OrganizationMemberNotFoundException(organizationMemberId));
    }


    /**
     * @throws OrganizationServiceUnavailableException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    public WebClient organizationClient() {
        var services = discoveryClient.getInstances(System.getenv("SERVICE_ORGANIZATIONS_QUERY_NAME"));

        if (services == null || services.isEmpty()) {
            throw new OrganizationServiceUnavailableException();
        }

        var serviceIndex = ThreadLocalRandom.current().nextInt(services.size()) % services.size();
        var service = services.get(serviceIndex);
        var url = service.getUri() + "/api/v1/organization-management";

        return WebClient.create(url);
    }
}
