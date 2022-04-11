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
public class ChangeIssueCommentContentCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final CommentContent commentContent;

    public static ChangeIssueCommentContentCommandBuilder builder() {
        return new ChangeIssueCommentContentCommandBuilder();
    }

    public static class ChangeIssueCommentContentCommandBuilder
            extends CommandBuilder<ChangeIssueCommentContentCommandBuilder, ChangeIssueCommentContentCommand> {
        public static final String COMMENT_CONTENT_FIELD_NAME = "commentContent";

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
