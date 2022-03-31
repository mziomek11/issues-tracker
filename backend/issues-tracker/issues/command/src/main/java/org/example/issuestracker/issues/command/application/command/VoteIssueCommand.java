package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.UUID;

public record VoteIssueCommand(IssueId issueId, VoterId voterId, VoteType voteType) {
    public VoteIssueCommand(UUID issueId, UUID voterId, VoteType voteType) {
        this(new IssueId(issueId), new VoterId(voterId), voteType);
    }
}
