package org.example.issuestracker.issues.command.ui.http.rest.v1.dto;

public record ChangeIssueCommentContentDto(String commentContent) {
    public static final String COMMENT_CONTENT_FIELD_NAME = "commentContent";
}
