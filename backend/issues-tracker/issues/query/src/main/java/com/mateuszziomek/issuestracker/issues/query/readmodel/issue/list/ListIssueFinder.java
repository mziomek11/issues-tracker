package com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list;

import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ListIssueFinder {
    private final ListIssueFilterQueryBuilderFactory queryBuilderFactory;

    public Flux<ListIssue> findByFilter(ListIssueFilter filter) {
        var qb = queryBuilderFactory.create();

        if (filter.getProjectId() != null) {
            qb.hasProjectId(filter.getProjectId());
        }

        return qb.execute();
    }
}
