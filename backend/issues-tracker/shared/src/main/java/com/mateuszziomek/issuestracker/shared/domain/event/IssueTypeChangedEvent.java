package com.mateuszziomek.issuestracker.shared.domain.event;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueTypeChangedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private IssueType issueType;

    public static IssueTypeChangedEventBuilder builder() {
        return new IssueTypeChangedEventBuilder();
    }

    private IssueTypeChangedEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            IssueType issueType
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId =projectId;
        this.memberId = memberId;
        this.issueType = issueType;
    }

    public static class IssueTypeChangedEventBuilder
            extends EventBuilder<IssueTypeChangedEventBuilder, IssueTypeChangedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID memberId;

        @NotNull
        private IssueType issueType;

        public IssueTypeChangedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueTypeChangedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueTypeChangedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueTypeChangedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        public IssueTypeChangedEventBuilder issueType(IssueType issueType) {
            this.issueType = issueType;
            return this;
        }

        @Override
        protected IssueTypeChangedEvent create() {
            return new IssueTypeChangedEvent(
                    issueId,
                    organizationId,
                    projectId,
                    memberId,
                    issueType
            );
        }
    }
}
