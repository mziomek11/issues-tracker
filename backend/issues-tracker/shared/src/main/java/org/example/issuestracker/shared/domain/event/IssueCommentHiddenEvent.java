package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueCommentHiddenEvent extends BaseEvent {
    private UUID commentId;

    public static IssueCommentHiddenEventBuilder builder() {
        return new IssueCommentHiddenEventBuilder();
    }

    private IssueCommentHiddenEvent(UUID issueId, UUID commentId) {
        super(issueId);

        this.commentId = commentId;
    }

    public static class IssueCommentHiddenEventBuilder
            extends EventBuilder<IssueCommentHiddenEventBuilder, IssueCommentHiddenEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        public IssueCommentHiddenEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentHiddenEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        @Override
        protected IssueCommentHiddenEvent create() {
            return new IssueCommentHiddenEvent(
                    issueId,
                    commentId
            );
        }
    }
}
