package com.mateuszziomek.issuestracker.organizations.query.application.query;

import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.query.Query;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetDetailsOrganizationQuery extends Query<Mono<DetailsOrganization>> {
    private final UUID memberId;
    private final UUID organizationId;
}
