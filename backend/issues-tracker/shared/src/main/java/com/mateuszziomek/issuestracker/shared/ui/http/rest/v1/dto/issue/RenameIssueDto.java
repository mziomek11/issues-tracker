package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.issue;

public record RenameIssueDto(String name) {
    public static final String ISSUE_NAME_FIELD_NAME = "name";
}