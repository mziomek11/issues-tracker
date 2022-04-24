package com.mateuszziomek.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueClosedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;

    public static IssueClosedEventBuilder builder() {
        return new IssueClosedEventBuilder();
    }

    private IssueClosedEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId = projectId;
        this.memberId = memberId;
    }

    public static class IssueClosedEventBuilder extends EventBuilder<IssueClosedEventBuilder, IssueClosedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID memberId;

        public IssueClosedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueClosedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueClosedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueClosedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        @Override
        protected IssueClosedEvent create() {
            return new IssueClosedEvent(
                    issueId,
                    organizationId,
                    projectId,
                    memberId
            );
        }
    }
}
