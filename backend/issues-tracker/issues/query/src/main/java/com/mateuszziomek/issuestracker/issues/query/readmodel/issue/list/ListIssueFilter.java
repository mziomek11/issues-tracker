package com.mateuszziomek.issuestracker.issues.query.readmodel.issue.list;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ListIssueFilter {
    private final UUID projectId;
}
