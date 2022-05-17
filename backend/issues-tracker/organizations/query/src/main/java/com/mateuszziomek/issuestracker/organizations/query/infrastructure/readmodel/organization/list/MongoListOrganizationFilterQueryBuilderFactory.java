package com.mateuszziomek.issuestracker.organizations.query.infrastructure.readmodel.organization.list;

import com.mateuszziomek.issuestracker.organizations.query.readmodel.organization.list.ListOrganizationFilterQueryBuilder;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.organization.list.ListOrganizationFilterQueryBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoListOrganizationFilterQueryBuilderFactory implements ListOrganizationFilterQueryBuilderFactory {
    private final ReactiveMongoTemplate template;

    @Override
    public ListOrganizationFilterQueryBuilder create() {
        return new MongoListOrganizationFilterQueryBuilder(template);
    }
}
