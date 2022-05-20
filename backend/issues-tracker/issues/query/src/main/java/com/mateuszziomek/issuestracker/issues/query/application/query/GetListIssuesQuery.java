package com.mateuszziomek.issuestracker.issues.query.application.query;

import com.mateuszziomek.cqrs.query.Query;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetListIssuesQuery extends Query {
    private final UUID memberId;
    private final UUID organizationId;
    private final UUID projectId;
}