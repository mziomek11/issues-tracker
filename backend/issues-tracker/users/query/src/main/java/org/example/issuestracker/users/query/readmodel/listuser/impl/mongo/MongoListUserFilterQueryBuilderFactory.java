package org.example.issuestracker.users.query.readmodel.listuser.impl.mongo;

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
