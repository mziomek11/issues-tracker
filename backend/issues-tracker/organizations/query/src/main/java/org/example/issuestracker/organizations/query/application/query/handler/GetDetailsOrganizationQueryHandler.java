package org.example.issuestracker.organizations.query.application.query.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.query.QueryHandler;
import org.example.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import org.example.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import org.example.issuestracker.organizations.query.readmodel.detailsorganization.DetailsOrganizationFinder;
import org.example.issuestracker.shared.readmodel.DetailsOrganization;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetDetailsOrganizationQueryHandler implements QueryHandler<GetDetailsOrganizationQuery, DetailsOrganization> {
    private final DetailsOrganizationFinder detailsOrganizationFinder;

    /**
     * @throws OrganizationNotFoundException if organization with given id does not exist
     */
    @Override
    public DetailsOrganization handle(GetDetailsOrganizationQuery query) {
        return detailsOrganizationFinder
                .findById(query.getOrganizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(query.getOrganizationId()));
    }
}
