package com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list;

import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ListIssueFilterQueryBuilder {
    ListIssueFilterQueryBuilder hasProjectId(UUID projectId);
    Flux<ListIssue> execute();
}
