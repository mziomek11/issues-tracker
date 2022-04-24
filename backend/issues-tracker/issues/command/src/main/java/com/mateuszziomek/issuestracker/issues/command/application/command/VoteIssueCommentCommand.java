package com.mateuszziomek.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandBuilder;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueId;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class VoteIssueCommentCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final VoteType voteType;
    private final IssueOrganizationDetails organizationDetails;


    public static VoteIssueCommentCommandBuilder builder() {
        return new VoteIssueCommentCommandBuilder();
    }

    public static class VoteIssueCommentCommandBuilder
            extends CommandBuilder<VoteIssueCommentCommandBuilder, VoteIssueCommentCommand> {
        public static final String VOTE_TYPE_FIELD_NAME = "voteType";

        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotNull
        private VoteType voteType;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public VoteIssueCommentCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public VoteIssueCommentCommandBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public VoteIssueCommentCommandBuilder voteType(VoteType voteType) {
            this.voteType = voteType;
            return this;
        }

        public VoteIssueCommentCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected VoteIssueCommentCommand create() {
            return new VoteIssueCommentCommand(
                    new IssueId(issueId),
                    new CommentId(commentId),
                    voteType,
                    organizationDetails
            );
        }
    }
}
