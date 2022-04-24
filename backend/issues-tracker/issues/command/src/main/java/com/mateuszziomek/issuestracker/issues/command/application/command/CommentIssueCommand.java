package com.mateuszziomek.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandBuilder;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentContent;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CommentIssueCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final CommentContent commentContent;
    private final IssueOrganizationDetails organizationDetails;

    public static CommentIssueCommandBuilder builder() {
        return new CommentIssueCommandBuilder();
    }

    public static class CommentIssueCommandBuilder
            extends CommandBuilder<CommentIssueCommandBuilder, CommentIssueCommand> {
        public static final String COMMENT_CONTENT_FIELD_NAME = "commentContent";

        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotBlank
        private String commentContent;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public CommentIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public CommentIssueCommandBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public CommentIssueCommandBuilder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        public CommentIssueCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected CommentIssueCommand create() {
            return new CommentIssueCommand(
                    new IssueId(issueId),
                    new CommentId(commentId),
                    new CommentContent(commentContent),
                    organizationDetails
            );
        }
    }
}
