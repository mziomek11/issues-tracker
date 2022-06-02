package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.issue;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;

public record VoteIssueDto(VoteType voteType) {
    public static final String VOTE_TYPE_FIELD_NAME = "voteType";
}
