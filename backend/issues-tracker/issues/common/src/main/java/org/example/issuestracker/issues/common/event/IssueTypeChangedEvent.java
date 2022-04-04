package org.example.issuestracker.issues.common.event;

import jakarta.validation.constraints.NotNull;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

import java.util.UUID;

public class IssueTypeChangedEvent extends BaseEvent {
    private final IssueType issueType;

    public static IssueTypeChangedEventBuilder builder() {
        return new IssueTypeChangedEventBuilder();
    }

    private IssueTypeChangedEvent(String issueId, IssueType issueType) {
        super(issueId);

        this.issueType = issueType;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public static class IssueTypeChangedEventBuilder
            extends EventBuilder<IssueTypeChangedEventBuilder, IssueTypeChangedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private IssueType issueType;

        public IssueTypeChangedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueTypeChangedEventBuilder issueType(IssueType issueType) {
            this.issueType = issueType;
            return this;
        }

        @Override
        protected IssueTypeChangedEvent create() {
            return new IssueTypeChangedEvent(
                    issueId.toString(),
                    issueType
            );
        }
    }
}
