package com.mateuszziomek.issuestracker.organizations.query.readmodel.listinvitation;

import com.mateuszziomek.issuestracker.shared.readmodel.ListInvitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ListInvitationFinder {
    private final ListInvitationFilterQueryBuilderFactory queryBuilderFactory;

    public Flux<ListInvitation> findByFilter(ListInvitationFilter filter) {
        var qb = queryBuilderFactory.create();

        if (filter.getMemberId() != null) {
            qb.containsMemberWithId(filter.getMemberId());
        }

        if (filter.getOrganizationId() != null) {
            qb.containsOrganizationWithId(filter.getOrganizationId());
        }

        return qb.execute();
    }
}
