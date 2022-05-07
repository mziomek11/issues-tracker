package com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization;

import com.mateuszziomek.issuestracker.shared.readmodel.ListOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListOrganizationFinder {
    private final ListOrganizationFilterQueryBuilderFactory queryBuilderFactory;

    public Flux<ListOrganization> findByFilter(ListOrganizationFilter filter) {
        var qb = queryBuilderFactory.create();

        if (filter.getMemberId() != null) {
            qb.containsMemberWithId(filter.getMemberId());
        }

        return qb.execute();
    }
}
