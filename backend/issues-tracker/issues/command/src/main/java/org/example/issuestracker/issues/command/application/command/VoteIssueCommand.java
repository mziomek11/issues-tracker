package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.UUID;

public class VoteIssueCommand {
    private final IssueId issueId;
    private final VoterId voterId;
    private final VoteType voteType;

    public VoteIssueCommand(UUID issueId, UUID voterId, VoteType voteType) {
        this.issueId = new IssueId(issueId);
        this.voterId = new VoterId(voterId);
        this.voteType = voteType;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public VoterId getVoterId() {
        return voterId;
    }

    public VoteType getVoteType() {
        return voteType;
    }
}
