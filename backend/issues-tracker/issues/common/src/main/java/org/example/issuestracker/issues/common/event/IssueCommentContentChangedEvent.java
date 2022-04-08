package org.example.issuestracker.issues.common.event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import java.util.Objects;
import java.util.UUID;

@Getter
public class IssueCommentContentChangedEvent extends BaseEvent {
    private final String commentId;
    private final String commentContent;

    public static IssueCommentContentChangedEventBuilder builder() {
        return new IssueCommentContentChangedEventBuilder();
    }

    private IssueCommentContentChangedEvent(String issueId, String commentId, String commentContent) {
        super(issueId);

        this.commentId = Objects.requireNonNull(commentId);
        this.commentContent = Objects.requireNonNull(commentContent);
    }

    public static class IssueCommentContentChangedEventBuilder
            extends EventBuilder<IssueCommentContentChangedEventBuilder, IssueCommentContentChangedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotBlank
        private String commentContent;

        public IssueCommentContentChangedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentContentChangedEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public IssueCommentContentChangedEventBuilder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        @Override
        protected IssueCommentContentChangedEvent create() {
            return new IssueCommentContentChangedEvent(
                    issueId.toString(),
                    commentId.toString(),
                    commentContent
            );
        }
    }
}
