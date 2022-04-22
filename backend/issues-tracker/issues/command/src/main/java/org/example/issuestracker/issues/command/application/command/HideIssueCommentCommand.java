package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class HideIssueCommentCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final IssueOrganizationDetails organizationDetails;

    public static HideIssueCommentCommandBuilder builder() {
        return new HideIssueCommentCommandBuilder();
    }

    public static class HideIssueCommentCommandBuilder
            extends CommandBuilder<HideIssueCommentCommandBuilder, HideIssueCommentCommand> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public HideIssueCommentCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public HideIssueCommentCommandBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public HideIssueCommentCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected HideIssueCommentCommand create() {
            return new HideIssueCommentCommand(
                    new IssueId(issueId),
                    new CommentId(commentId),
                    organizationDetails
            );
        }
    }
}
