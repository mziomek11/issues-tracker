package com.mateuszziomek.issuestracker.organizations.query.infrastructure.readmodel.listorganization;

import com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization.ListOrganizationFilterQueryBuilder;
import com.mateuszziomek.issuestracker.organizations.query.readmodel.listorganization.ListOrganizationFilterQueryBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoListOrganizationFilterQueryBuilderFactory implements ListOrganizationFilterQueryBuilderFactory {
    private final MongoTemplate template;

    @Override
    public ListOrganizationFilterQueryBuilder create() {
        return new MongoListOrganizationFilterQueryBuilder(template);
    }
}
