package com.mateuszziomek.issuestracker.issues.query.infrastructure.readmodel.issue.list;

import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list.ListIssueFilterQueryBuilder;
import com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list.ListIssueMapper;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RequiredArgsConstructor
public class MongoListIssueFilterQueryBuilder implements ListIssueFilterQueryBuilder {
    private final ReactiveMongoTemplate template;
    private final Query query = new Query();

    @Override
    public ListIssueFilterQueryBuilder hasProjectId(UUID projectId) {
        query.addCriteria(Criteria.where("projectId").is(projectId));
        return this;
    }

    @Override
    public Flux<ListIssue> execute() {
        return template
                .find(query, Issue.class)
                .map(ListIssueMapper::fromModel);
    }
}
