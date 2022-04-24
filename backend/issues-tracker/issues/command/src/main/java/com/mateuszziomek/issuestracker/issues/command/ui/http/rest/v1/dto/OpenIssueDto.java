package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;

public record OpenIssueDto(String name, String content, IssueType type) {
    public static final String ISSUE_TYPE_FIELD_NAME = "type";
    public static final String ISSUE_CONTENT_FIELD_NAME = "content";
    public static final String ISSUE_NAME_FIELD_NAME = "name";
}
