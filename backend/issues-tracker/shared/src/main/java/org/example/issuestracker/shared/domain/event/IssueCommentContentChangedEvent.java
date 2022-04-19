package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueCommentContentChangedEvent extends BaseEvent {
    private UUID commentId;
    private String commentContent;

    public static IssueCommentContentChangedEventBuilder builder() {
        return new IssueCommentContentChangedEventBuilder();
    }

    private IssueCommentContentChangedEvent(UUID issueId, UUID commentId, String commentContent) {
        super(issueId);

        this.commentId = commentId;
        this.commentContent = commentContent;
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
                    issueId,
                    commentId,
                    commentContent
            );
        }
    }
}
