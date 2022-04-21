package org.example.issuestracker.users.query.infrastructure.readmodel.listuser;

import lombok.RequiredArgsConstructor;
import org.example.issuestracker.users.query.readmodel.listuser.ListUserFilterQueryBuilder;
import org.example.issuestracker.users.query.readmodel.listuser.ListUserFilterQueryBuilderFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoListUserFilterQueryBuilderFactory implements ListUserFilterQueryBuilderFactory {
    private final MongoTemplate template;

    @Override
    public ListUserFilterQueryBuilder create() {
        return new MongoListUserFilterQueryBuilder(template);
    }
}
