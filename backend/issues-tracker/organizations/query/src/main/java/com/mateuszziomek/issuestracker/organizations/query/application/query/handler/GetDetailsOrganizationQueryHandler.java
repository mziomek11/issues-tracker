package com.mateuszziomek.issuestracker.organizations.query.application.query.handler;

import com.mateuszziomek.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.organization.details.DetailsOrganizationFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetDetailsOrganizationQueryHandler implements QueryHandler<Mono<DetailsOrganization>, GetDetailsOrganizationQuery> {
    private final DetailsOrganizationFinder detailsOrganizationFinder;

    /**
     * @throws OrganizationNotFoundException if organization with given id does not exist
     */
    @Override
    public Mono<DetailsOrganization> handle(GetDetailsOrganizationQuery query) {
        return detailsOrganizationFinder
                .findById(query.getOrganizationId())
                .filter(organization -> organization.hasMemberWithId(query.getMemberId()))
                .switchIfEmpty(Mono.error(new OrganizationNotFoundException(query.getOrganizationId())));
    }
}
