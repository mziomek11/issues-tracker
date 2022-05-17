package com.mateuszziomek.issuestracker.organizations.query.application.query.handler;

import com.mateuszziomek.cqrs.query.QueryHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.query.GetListInvitationsQuery;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.invitation.list.ListInvitationFilter;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.invitation.list.ListInvitationFinder;
import com.mateuszziomek.issuestracker.shared.readmodel.invitation.ListInvitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetListInvitationsQueryHandler implements QueryHandler<GetListInvitationsQuery, Flux<ListInvitation>> {
    private final ListInvitationFinder listInvitationFinder;

    @Override
    public Flux<ListInvitation> handle(GetListInvitationsQuery query) {
       var filter = ListInvitationFilter
               .builder()
               .memberId(query.getMemberId())
               .organizationId(query.getOrganizationId())
               .build();

       return listInvitationFinder.findByFilter(filter);
    }
}
