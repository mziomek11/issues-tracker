package org.example.issuestracker.issues.common.domain.event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueRenamedEvent extends BaseEvent {
    private String issueName;

    public static IssueRenamedEventBuilder builder() {
        return new IssueRenamedEventBuilder();
    }

    private IssueRenamedEvent(String issueId, String issueName) {
        super(issueId);

        this.issueName = Objects.requireNonNull(issueName);
    }

    public static class IssueRenamedEventBuilder
            extends EventBuilder<IssueRenamedEventBuilder, IssueRenamedEvent> {
        @NotNull
        private UUID issueId;

        @NotBlank
        private String issueName;

        public IssueRenamedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueRenamedEventBuilder issueName(String issueName) {
            this.issueName = issueName;
            return this;
        }

        @Override
        protected IssueRenamedEvent create() {
            return new IssueRenamedEvent(
                    issueId.toString(),
                    issueName
            );
        }
    }
}
