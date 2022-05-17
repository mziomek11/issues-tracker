package com.mateuszziomek.issuestracker.organizations.query.readmodel.invitation.list;

import com.mateuszziomek.issuestracker.shared.readmodel.invitation.ListInvitation;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ListInvitationFilterQueryBuilder {
    ListInvitationFilterQueryBuilder containsMemberWithId(UUID memberId);
    ListInvitationFilterQueryBuilder containsOrganizationWithId(UUID memberId);
    Flux<ListInvitation> execute();
}
