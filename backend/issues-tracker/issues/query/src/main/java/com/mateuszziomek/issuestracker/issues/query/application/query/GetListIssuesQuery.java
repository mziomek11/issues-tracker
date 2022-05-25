package com.mateuszziomek.issuestracker.issues.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.ListIssue;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Getter
@Builder
public class GetListIssuesQuery extends Query<Flux<ListIssue>> {
    private final UUID memberId;
    private final UUID organizationId;
    private final UUID projectId;
}
