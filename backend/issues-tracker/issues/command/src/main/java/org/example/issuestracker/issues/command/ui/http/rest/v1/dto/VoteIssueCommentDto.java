package org.example.issuestracker.issues.command.ui.http.rest.v1.dto;

import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.UUID;

public record VoteIssueCommentDto(UUID voterId, VoteType voteType) {
    public static final String VOTER_ID_FIELD_NAME = "voterId";
    public static final String VOTE_TYPE_FIELD_NAME = "voteType";
}
