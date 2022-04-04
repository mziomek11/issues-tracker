package org.example.issuestracker.issues.command.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public class ChangeIssueCommentContentCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final CommentContent commentContent;

    public static ChangeIssueCommentContentCommandBuilder builder() {
        return new ChangeIssueCommentContentCommandBuilder();
    }

    private ChangeIssueCommentContentCommand(IssueId issueId, CommentId commentId, CommentContent commentContent) {
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

    public static class ChangeIssueCommentContentCommandBuilder
            extends CommandBuilder<ChangeIssueCommentContentCommandBuilder, ChangeIssueCommentContentCommand> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotBlank
        private String commentContent;

        public ChangeIssueCommentContentCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public ChangeIssueCommentContentCommandBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public ChangeIssueCommentContentCommandBuilder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        @Override
        protected ChangeIssueCommentContentCommand create() {
            return new ChangeIssueCommentContentCommand(
                    new IssueId(issueId),
                    new CommentId(commentId),
                    new CommentContent(commentContent)
            );
        }
    }
}
