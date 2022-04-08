package org.example.issuestracker.issues.common.event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

import java.util.Objects;
import java.util.UUID;

@Getter
public class IssueOpenedEvent extends BaseEvent {
    private final IssueType issueType;
    private final String issueContent;
    private final String issueName;

    public static IssueOpenedEventBuilder builder() {
        return new IssueOpenedEventBuilder();
    }

    private IssueOpenedEvent(String issueId, IssueType issueType, String issueContent, String issueName) {
        super(issueId);

        this.issueType = issueType;
        this.issueContent = Objects.requireNonNull(issueContent);
        this.issueName = Objects.requireNonNull(issueName);
    }

    public static class IssueOpenedEventBuilder
            extends EventBuilder<IssueOpenedEventBuilder, IssueOpenedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private IssueType issueType;

        @NotBlank
        private String issueContent;

        @NotBlank
        private String issueName;

        public IssueOpenedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueOpenedEventBuilder issueType(IssueType issueType) {
            this.issueType = issueType;
            return this;
        }

        public IssueOpenedEventBuilder issueContent(String issueContent) {
            this.issueContent = issueContent;
            return this;
        }

        public IssueOpenedEventBuilder issueName(String issueName) {
            this.issueName = issueName;
            return this;
        }

        @Override
        protected IssueOpenedEvent create() {
            return new IssueOpenedEvent(
                    issueId.toString(),
                    issueType,
                    issueContent,
                    issueName
            );
        }
    }
}
