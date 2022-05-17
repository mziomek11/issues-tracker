package com.mateuszziomek.issuestracker.issues.query.application.gateway.organization;

import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrganizationGateway {
    /**
     * @throws OrganizationMemberNotFoundException if member with given id does not exist in organization
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws OrganizationProjectNotFoundException if project with given id does not exist in organization
     * @throws OrganizationServiceUnavailableException if organization service is not available
     */
    Mono<Boolean> ensureOrganizationHasProjectAndMember(UUID organizationId, UUID projectId, UUID memberId);
}
