package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.issue;

public record ChangeIssueContentDto(String content) {
    public static final String ISSUE_CONTENT_FIELD_NAME = "content";
}
