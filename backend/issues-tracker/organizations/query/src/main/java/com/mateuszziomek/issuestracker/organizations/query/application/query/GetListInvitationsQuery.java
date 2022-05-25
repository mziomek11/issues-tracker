package com.mateuszziomek.issuestracker.organizations.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.readmodel.invitation.ListInvitation;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Getter
@Builder
public class GetListInvitationsQuery extends Query<Flux<ListInvitation>> {
    private final UUID memberId;
    private final UUID organizationId;
}
