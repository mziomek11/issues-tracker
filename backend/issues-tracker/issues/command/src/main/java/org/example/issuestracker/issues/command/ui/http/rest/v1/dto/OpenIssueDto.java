package org.example.issuestracker.issues.command.ui.http.rest.v1.dto;

import org.example.issuestracker.shared.domain.valueobject.IssueType;

public record OpenIssueDto(String issueName, String issueContent, IssueType issueType) {
    public static final String ISSUE_TYPE_FIELD_NAME = "issueType";
    public static final String ISSUE_CONTENT_FIELD_NAME = "issueContent";
    public static final String ISSUE_NAME_FIELD_NAME = "issueName";
}
