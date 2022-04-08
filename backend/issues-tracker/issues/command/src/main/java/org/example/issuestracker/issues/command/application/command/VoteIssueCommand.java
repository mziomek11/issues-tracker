package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class VoteIssueCommand {
    private final IssueId issueId;
    private final VoterId voterId;
    private final VoteType voteType;

    public static VoteIssueCommandBuilder builder() {
        return new VoteIssueCommandBuilder();
    }

    public static class VoteIssueCommandBuilder extends CommandBuilder<VoteIssueCommandBuilder, VoteIssueCommand> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID voterId;

        @NotNull
        private VoteType voteType;

        public VoteIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public VoteIssueCommandBuilder voterId(UUID voterId) {
            this.voterId = voterId;
            return this;
        }

        public VoteIssueCommandBuilder voteType(VoteType voteType) {
            this.voteType = voteType;
            return this;
        }

        @Override
        protected VoteIssueCommand create() {
            return new VoteIssueCommand(
                    new IssueId(issueId),
                    new VoterId(voterId),
                    voteType
            );
        }
    }
}
