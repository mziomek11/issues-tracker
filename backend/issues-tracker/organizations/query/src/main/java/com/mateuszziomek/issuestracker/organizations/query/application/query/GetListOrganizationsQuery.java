package com.mateuszziomek.issuestracker.organizations.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.ListOrganization;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetListOrganizationsQuery extends Query<Flux<ListOrganization>> {
    private final UUID memberId;
}
