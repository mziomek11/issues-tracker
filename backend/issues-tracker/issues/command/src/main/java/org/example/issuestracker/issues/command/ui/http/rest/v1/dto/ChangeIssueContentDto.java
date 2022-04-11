package org.example.issuestracker.issues.command.ui.http.rest.v1.dto;

public record ChangeIssueContentDto(String issueContent) {
    public static final String ISSUE_CONTENT_FIELD_NAME = "issueContent";
}
