package org.example.issuestracker.issues.command.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.cqrs.command.CommandBuilder;
import org.example.cqrs.command.CommandValidationException;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public class CommentIssueCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final CommentContent commentContent;

    public static CommentIssueCommandBuilder builder() {
        return new CommentIssueCommandBuilder();
    }

    private CommentIssueCommand(IssueId issueId, CommentId commentId, CommentContent commentContent) {
        this.issueId = issueId;
        this.commentId = commentId;
        this.commentContent = commentContent;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public CommentContent getCommentContent() {
        return commentContent;
    }

    public static class CommentIssueCommandBuilder
            extends CommandBuilder<CommentIssueCommandBuilder, CommentIssueCommand> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotBlank
        private String commentContent;

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

        /**
         * @throws CommandValidationException see {@linkplain CommandBuilder#build()}
         */
        @Override
        protected CommentIssueCommand create() {
            return new CommentIssueCommand(
                    new IssueId(issueId),
                    new CommentId(commentId),
                    new CommentContent(commentContent)
            );
        }
    }
}
