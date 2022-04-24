package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;

public record ChangeIssueTypeDto(IssueType type){
    public static final String ISSUE_TYPE_FIELD_NAME = "type";
}