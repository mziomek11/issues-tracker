package com.mateuszziomek.issuestracker.organizations.query.application.query.handler;

import com.mateuszziomek.issuestracker.organizations.query.application.query.exception.OrganizationNotFoundException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetDetailsOrganizationQuery;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.detailsorganization.DetailsOrganizationFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.DetailsOrganization;
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