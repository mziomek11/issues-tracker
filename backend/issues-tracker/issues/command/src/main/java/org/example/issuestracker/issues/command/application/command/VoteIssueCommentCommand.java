package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.shared.domain.valueobject.VoteType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class VoteIssueCommentCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final VoterId voterId;
    private final VoteType voteType;

    public static VoteIssueCommentCommandBuilder builder() {
        return new VoteIssueCommentCommandBuilder();
    }

    public static class VoteIssueCommentCommandBuilder
            extends CommandBuilder<VoteIssueCommentCommandBuilder, VoteIssueCommentCommand> {
        public static final String VOTER_ID_FIELD_NAME = "voterId";
        public static final String VOTE_TYPE_FIELD_NAME = "voteType";

        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotNull
        private UUID voterId;

        @NotNull
        private VoteType voteType;

        public VoteIssueCommentCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public VoteIssueCommentCommandBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public VoteIssueCommentCommandBuilder voterId(UUID voterId) {
            this.voterId = voterId;
            return this;
        }

        public VoteIssueCommentCommandBuilder voteType(VoteType voteType) {
            this.voteType = voteType;
            return this;
        }

        @Override
        protected VoteIssueCommentCommand create() {
            return new VoteIssueCommentCommand(
                    new IssueId(issueId),
                    new CommentId(commentId),
                    new VoterId(voterId),
                    voteType
            );
        }
    }
}
