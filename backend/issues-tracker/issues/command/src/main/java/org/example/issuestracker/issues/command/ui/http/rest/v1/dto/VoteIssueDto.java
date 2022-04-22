package org.example.issuestracker.issues.command.ui.http.rest.v1.dto;

import org.example.issuestracker.shared.domain.valueobject.VoteType;

public record VoteIssueDto(VoteType voteType) {
    public static final String VOTE_TYPE_FIELD_NAME = "voteType";
}
