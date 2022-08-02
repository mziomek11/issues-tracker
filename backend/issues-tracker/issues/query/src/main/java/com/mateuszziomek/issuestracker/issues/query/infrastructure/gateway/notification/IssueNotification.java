package com.mateuszziomek.issuestracker.issues.query.infrastructure.gateway.notification;

import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;

import java.util.Map;
import java.util.UUID;

public class IssueNotification {
    private static final String ORGANIZATION_ID = "organizationId";
    private static final String PROJECT_ID = "projectId";
    private static final String ISSUE_ID = "issueId";
    private static final String COMMENT_ID = "commentId";
    private static final String MEMBER_ID = "memberId";

    private IssueNotification() {}

    public static Map<String, ? extends Object> issue(Issue issue, UUID memberId) {
        return Map.of(
                ORGANIZATION_ID,
                issue.getOrganizationId(),
                PROJECT_ID,
                issue.getProjectId(),
                ISSUE_ID,
                issue.getId(),
                MEMBER_ID,
                memberId
        );
    }

    public static Map<String, ? extends Object> issueComment(Issue issue, UUID memberId, UUID commentId) {
        return Map.of(
                ORGANIZATION_ID,
                issue.getOrganizationId(),
                PROJECT_ID,
                issue.getProjectId(),
                ISSUE_ID,
                issue.getId(),
                COMMENT_ID,
                commentId,
                MEMBER_ID,
                memberId
        );
    }
}
