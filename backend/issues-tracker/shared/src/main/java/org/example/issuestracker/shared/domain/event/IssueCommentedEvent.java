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
public class IssueCommentedEvent extends BaseEvent {
    private UUID commentId;
    private String commentContent;

    public static IssueCommentedEventBuilder builder() {
        return new IssueCommentedEventBuilder();
    }

    private IssueCommentedEvent(UUID issueId, UUID commentId, String commentContent) {
        super(issueId);

        this.commentId = commentId;
        this.commentContent = commentContent;
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
                    issueId,
                    commentId,
                    commentContent
            );
        }
    }
}
