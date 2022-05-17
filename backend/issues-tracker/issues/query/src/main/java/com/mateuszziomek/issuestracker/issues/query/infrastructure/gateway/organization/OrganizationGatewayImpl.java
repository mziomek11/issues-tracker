package com.mateuszziomek.issuestracker.issues.query.infrastructure.gateway.organization;

import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrganizationGatewayImpl implements OrganizationGateway {
    private final ReactiveOrganizationRestClient organizationRestClient;

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     * @throws OrganizationServiceUnavailableException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(UUID, UUID, UUID)}
     */
    @Override
    public Mono<Void> ensureOrganizationHasProjectAndMember(UUID organizationId, UUID projectId, UUID memberId) {
        try {
            return organizationRestClient
                    .getOrganizationById(organizationId)
                    .flatMap((organization) -> {
                        if (!isProjectInOrganization(organization, projectId)) {
                            return Mono.error(new OrganizationProjectNotFoundException(projectId));
                        }

                        if (!isMemberInOrganization(organization, memberId)) {
                            return Mono.error(new OrganizationMemberNotFoundException(memberId));
                        }

                        return Mono.just(organization).then();
                    })
                    .switchIfEmpty(Mono.error(new OrganizationNotFoundException(organizationId)));
        } catch (com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception.OrganizationServiceUnavailableException ex) {
            throw new OrganizationServiceUnavailableException();
        }
    }

    private boolean isProjectInOrganization(DetailsOrganization organization, UUID projectId) {
        return organization
                .getProjects()
                .stream()
                .anyMatch((project -> project.getId().equals(projectId)));
    }

    private boolean isMemberInOrganization(DetailsOrganization organization, UUID memberId) {
        return organization
                .getMembers()
                .stream()
                .anyMatch((member -> member.getId().equals(memberId)));
    }
}
