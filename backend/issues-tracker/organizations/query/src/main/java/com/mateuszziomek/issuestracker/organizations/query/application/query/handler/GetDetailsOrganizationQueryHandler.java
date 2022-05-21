package com.mateuszziomek.issuestracker.organizations.query.application.query.handler;

import com.mateuszziomek.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.organization.details.DetailsOrganizationFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetDetailsOrganizationQueryHandler implements QueryHandler<GetDetailsOrganizationQuery, Mono<DetailsOrganization>> {
    private final DetailsOrganizationFinder detailsOrganizationFinder;

    /**
     * @throws AccessDeniedException if user role is not {@link UserRole#SYSTEM}
     * @throws OrganizationNotFoundException if organization with given id does not exist
     */
    @Override
    public Mono<DetailsOrganization> handle(GetDetailsOrganizationQuery query) {
        if (!UserRole.SYSTEM.equals(query.getUserRole())) {
            throw new AccessDeniedException();
        }

        return detailsOrganizationFinder
                .findById(query.getOrganizationId())
                .switchIfEmpty(Mono.error(new OrganizationNotFoundException(query.getOrganizationId())));
    }
}
