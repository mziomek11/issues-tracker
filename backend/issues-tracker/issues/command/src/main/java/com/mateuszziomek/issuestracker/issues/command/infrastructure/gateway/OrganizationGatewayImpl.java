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
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationGatewayImpl implements OrganizationGateway {
    private final ReactiveOrganizationRestClient organizationRestClient;

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException  see{@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationServiceUnavailableException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void ensureOrganizationHasProjectAndMember(IssueOrganizationDetails organizationDetails) {
        var organization = findOrganizationByIdOrThrow(organizationDetails.organizationId());
        ensureOrganizationHasProject(organization, organizationDetails.projectId());
        ensureOrganizationHasMember(organization, organizationDetails.memberId());
    }

    /**
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationServiceUnavailableException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    private DetailsOrganization findOrganizationByIdOrThrow(OrganizationId organizationId) {
        try {
            var organization = organizationRestClient
                    .getOrganizationById(organizationId.getValue())
                    .block();

            if (organization == null) {
                throw new OrganizationNotFoundException(organizationId);
            }

            return organization;
        } catch (com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception.OrganizationNotFoundException ex) {
            throw new OrganizationNotFoundException(organizationId);
        } catch (com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception.OrganizationServiceUnavailableException ex) {
            throw new OrganizationServiceUnavailableException();
        }
    }

    /**
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    private void ensureOrganizationHasProject(DetailsOrganization organization, OrganizationProjectId organizationProjectId) {
        var hasOrganizationProject = organization
                .getProjects()
                .stream()
                .anyMatch(project -> project.getId().equals(organizationProjectId.getValue()));

        if (!hasOrganizationProject) {
            throw new OrganizationProjectNotFoundException(organizationProjectId);
        }
    }

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    private void ensureOrganizationHasMember(DetailsOrganization organization, OrganizationMemberId organizationMemberId) {
        var hasOrganizationMember = organization
                .getMembers()
                .stream()
                .anyMatch(member -> member.getId().equals(organizationMemberId.getValue()));

        if (!hasOrganizationMember) {
            throw new OrganizationMemberNotFoundException(organizationMemberId);
        }
    }
}
