package org.example.issuestracker.issues.common.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import java.util.Objects;
import java.util.UUID;

public class IssueContentChangedEvent extends BaseEvent {
    private final String issueContent;

    public static IssueCommentVotedEventBuilder builder() {
        return new IssueCommentVotedEventBuilder();
    }

    private IssueContentChangedEvent(String issueId, String issueContent) {
        super(issueId);

        this.issueContent = Objects.requireNonNull(issueContent);
    }

    public String getIssueContent() {
        return issueContent;
    }

    public static class IssueCommentVotedEventBuilder
            extends EventBuilder<IssueCommentVotedEventBuilder, IssueContentChangedEvent> {
        @NotNull
        private UUID issueId;

        @NotBlank
        private String issueContent;

        public IssueCommentVotedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentVotedEventBuilder issueContent(String issueContent) {
            this.issueContent = issueContent;
            return this;
        }

        @Override
        protected IssueContentChangedEvent create() {
            return new IssueContentChangedEvent(
                    issueId.toString(),
                    issueContent
            );
        }
    }
}
