package org.example.issuestracker.issues.command.ui.http.rest.v1.dto;

public record CommentIssueDto(String content) {
    public static final String COMMENT_CONTENT_FIELD_NAME = "content";
}
