package com.mateuszziomek.issuestracker.issues.query.application.service.organization;

import com.mateuszziomek.issuestracker.issues.query.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.service.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.domain.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    /**
     * @throws OrganizationMemberNotFoundException if member with given id does not exist in organization
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws OrganizationProjectNotFoundException if project with given id does not exist in organization
     */
    public Mono<Boolean> ensureOrganizationHasProjectAndMember(UUID organizationId, UUID projectId, UUID memberId) {
        return organizationRepository
                .findById(organizationId)
                .switchIfEmpty(Mono.error(new OrganizationNotFoundException(organizationId)))
                .flatMap(organization -> {
                    if (!organization.getProjectIds().contains(projectId)) {
                        return Mono.error(new OrganizationProjectNotFoundException(projectId));
                    }

                    if (!organization.getMemberIds().contains(memberId)) {
                        return Mono.error(new OrganizationMemberNotFoundException(memberId));
                    }

                    return Mono.just(true);
                });
    }
}
