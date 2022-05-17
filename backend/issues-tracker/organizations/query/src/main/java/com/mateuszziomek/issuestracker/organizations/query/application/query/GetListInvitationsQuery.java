package com.mateuszziomek.issuestracker.organizations.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetListInvitationsQuery extends Query {
    private final UUID memberId;
    private final UUID organizationId;
}
