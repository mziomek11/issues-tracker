package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReactiveOrganizationRestClient {
    /**
     * @throws OrganizationServiceUnavailableException if organization service in unavailable
     */
    Mono<DetailsOrganization> getOrganizationById(UUID organizationId);
}
