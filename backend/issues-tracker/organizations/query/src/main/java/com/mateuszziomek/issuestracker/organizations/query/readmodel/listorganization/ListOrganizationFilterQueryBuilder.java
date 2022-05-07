package com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization;

import com.mateuszziomek.issuestracker.shared.readmodel.ListOrganization;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ListOrganizationFilterQueryBuilder {
    ListOrganizationFilterQueryBuilder containsMemberWithId(UUID memberId);
    Flux<ListOrganization> execute();
}
