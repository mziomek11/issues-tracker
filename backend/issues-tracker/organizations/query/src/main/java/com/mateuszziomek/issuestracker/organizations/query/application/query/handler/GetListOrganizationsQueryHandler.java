package com.mateuszziomek.issuestracker.organizations.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetListOrganizationsQuery;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization.ListOrganizationFilter;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization.ListOrganizationFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.ListOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetListOrganizationsQueryHandler implements QueryHandler<GetListOrganizationsQuery, Flux<ListOrganization>> {
    private final ListOrganizationFinder listOrganizationFinder;

    @Override
    public Flux<ListOrganization> handle(GetListOrganizationsQuery query) {
        var filter = ListOrganizationFilter
                .builder()
                .memberId(query.getMemberId())
                .build();

        return listOrganizationFinder.findByFilter(filter);
    }
}