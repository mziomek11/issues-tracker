package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto;

public record ChangeIssueContentDto(String content) {
    public static final String ISSUE_CONTENT_FIELD_NAME = "content";
}
