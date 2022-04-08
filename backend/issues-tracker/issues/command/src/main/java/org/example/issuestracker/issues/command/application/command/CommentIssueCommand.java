package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CommentIssueCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final CommentContent commentContent;

    public static CommentIssueCommandBuilder builder() {
        return new CommentIssueCommandBuilder();
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
