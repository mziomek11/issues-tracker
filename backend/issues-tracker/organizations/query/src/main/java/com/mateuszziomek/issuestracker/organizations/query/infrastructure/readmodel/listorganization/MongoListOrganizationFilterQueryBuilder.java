package com.mateuszziomek.issuestracker.organizations.query.infrastructure.readmodel.listorganization;

import com.mateuszziomek.issuestracker.organizations.query.domain.Organization;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization.ListOrganizationFilterQueryBuilder;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization.ListOrganizationMapper;
import com.mateuszziomek.issuestracker.shared.readmodel.ListOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RequiredArgsConstructor
public class MongoListOrganizationFilterQueryBuilder implements ListOrganizationFilterQueryBuilder {
    private final ReactiveMongoTemplate template;
    private final Query query = new Query();

    @Override
    public ListOrganizationFilterQueryBuilder containsMemberWithId(UUID memberId) {
        query.addCriteria(Criteria.where("members.id").is(memberId));
        return this;
    }

    @Override
    public Flux<ListOrganization> execute() {
        return template
                .find(query, Organization.class)
                .map(ListOrganizationMapper::fromModel);
    }
}
