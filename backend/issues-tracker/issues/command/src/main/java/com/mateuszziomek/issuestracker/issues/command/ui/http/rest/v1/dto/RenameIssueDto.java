package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto;

public record RenameIssueDto(String name) {
    public static final String ISSUE_NAME_FIELD_NAME = "name";
}