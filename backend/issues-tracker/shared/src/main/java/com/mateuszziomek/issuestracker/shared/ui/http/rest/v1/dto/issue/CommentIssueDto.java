package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.issue;

public record CommentIssueDto(String content) {
    public static final String COMMENT_CONTENT_FIELD_NAME = "content";
}
