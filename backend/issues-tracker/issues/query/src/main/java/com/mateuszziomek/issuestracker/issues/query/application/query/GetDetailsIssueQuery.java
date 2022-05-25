package com.mateuszziomek.issuestracker.issues.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.issuestracker.shared.readmodel.issue.DetailsIssue;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Getter
@Builder
public class GetDetailsIssueQuery extends Query<Mono<DetailsIssue>> {
    private final UUID memberId;
    private final UUID organizationId;
    private final UUID projectId;
    private final UUID issueId;
}
