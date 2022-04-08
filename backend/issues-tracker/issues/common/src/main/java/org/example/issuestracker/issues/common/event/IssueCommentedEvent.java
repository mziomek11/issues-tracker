package org.example.issuestracker.issues.common.event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import java.util.Objects;
import java.util.UUID;

public class IssueCommentedEvent extends BaseEvent {
    private final String commentId;
    private final String commentContent;

    public static IssueCommentedEventBuilder builder() {
        return new IssueCommentedEventBuilder();
    }

    private IssueCommentedEvent(String issueId, String commentId, String commentContent) {
        super(issueId);

        this.commentId = Objects.requireNonNull(commentId);
        this.commentContent = Objects.requireNonNull(commentContent);
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public static class IssueCommentedEventBuilder
            extends EventBuilder<IssueCommentedEventBuilder, IssueCommentedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotBlank
        private String commentContent;

        public IssueCommentedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentedEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public IssueCommentedEventBuilder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        @Override
        protected IssueCommentedEvent create() {
            return new IssueCommentedEvent(
                    issueId.toString(),
                    commentId.toString(),
                    commentContent
            );
        }
    }
}
