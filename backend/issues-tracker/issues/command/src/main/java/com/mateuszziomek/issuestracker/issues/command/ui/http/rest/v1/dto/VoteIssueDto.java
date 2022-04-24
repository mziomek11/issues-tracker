package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1.dto;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;

public record VoteIssueDto(VoteType voteType) {
    public static final String VOTE_TYPE_FIELD_NAME = "voteType";
}
