package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.AbstractRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class ReactiveOrganizationRestClientV1 extends AbstractRestClient implements ReactiveOrganizationRestClient {
    private static final String SERVICE_PATH = "/api/v1/organization-management";
    private static final String GET_ORGANIZATION_PATH = "/organizations/{organizationId}";

    public ReactiveOrganizationRestClientV1(DiscoveryClient discoveryClient, String serviceName) {
        super(discoveryClient, serviceName, SERVICE_PATH);
    }

    /**
     * @throws OrganizationServiceUnavailableException see {@link ReactiveOrganizationRestClient#getOrganizationById(UUID)}
     */
    @Override
    public Mono<DetailsOrganization> getOrganizationById(UUID organizationId) {
        return createClient()
                .get()
                .uri(GET_ORGANIZATION_PATH, organizationId)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.SYSTEM.toString())
                .retrieve()
                .bodyToMono(DetailsOrganization.class);
    }

    @Override
    protected IllegalStateException createServiceUnavailableException() {
        return new OrganizationServiceUnavailableException();
    }
}
