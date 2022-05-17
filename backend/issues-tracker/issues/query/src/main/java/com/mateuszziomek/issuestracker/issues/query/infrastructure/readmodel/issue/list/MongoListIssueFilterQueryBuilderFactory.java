package com.mateuszziomek.issuestracker.issues.query.infrastructure.readmodel.issue.list;

import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list.ListIssueFilterQueryBuilder;
import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list.ListIssueFilterQueryBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoListIssueFilterQueryBuilderFactory implements ListIssueFilterQueryBuilderFactory {
    private final ReactiveMongoTemplate template;

    @Override
    public ListIssueFilterQueryBuilder create() {
        return new MongoListIssueFilterQueryBuilder(template);
    }
}
