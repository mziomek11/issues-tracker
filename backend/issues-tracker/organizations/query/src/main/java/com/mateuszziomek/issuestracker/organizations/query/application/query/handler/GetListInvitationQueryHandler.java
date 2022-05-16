package com.mateuszziomek.issuestracker.organizations.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetListInvitationQuery;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listinvitation.ListInvitationFilter;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listinvitation.ListInvitationFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.ListInvitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetListInvitationQueryHandler implements QueryHandler<GetListInvitationQuery, Flux<ListInvitation>> {
    private final ListInvitationFinder listInvitationFinder;

    @Override
    public Flux<ListInvitation> handle(GetListInvitationQuery query) {
       var filter = ListInvitationFilter
               .builder()
               .memberId(query.getMemberId())
               .organizationId(query.getOrganizationId())
               .build();

       return listInvitationFinder.findByFilter(filter);
    }
}
