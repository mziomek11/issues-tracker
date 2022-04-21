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
public class IssueRenamedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private String issueName;

    public static IssueRenamedEventBuilder builder() {
        return new IssueRenamedEventBuilder();
    }

    private IssueRenamedEvent(UUID issueId, UUID organizationId, UUID projectId, UUID memberId, String issueName) {
        super(issueId);

        this.issueName = issueName;
        this.organizationId = organizationId;
        this.projectId = projectId;
        this.memberId = memberId;
    }

    public static class IssueRenamedEventBuilder
            extends EventBuilder<IssueRenamedEventBuilder, IssueRenamedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID memberId;

        @NotBlank
        private String issueName;

        public IssueRenamedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueRenamedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueRenamedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueRenamedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        public IssueRenamedEventBuilder issueName(String issueName) {
            this.issueName = issueName;
            return this;
        }

        @Override
        protected IssueRenamedEvent create() {
            return new IssueRenamedEvent(
                    issueId,
                    organizationId,
                    projectId,
                    memberId,
                    issueName
                );
        }
    }
}
