package org.example.issuestracker.issues.command.ui.http.rest.v1.dto;

public record RenameIssueDto(String issueName) {
    public static final String ISSUE_NAME_FIELD_NAME = "issueName";
}