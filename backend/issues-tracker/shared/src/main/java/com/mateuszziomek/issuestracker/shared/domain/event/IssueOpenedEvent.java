package com.mateuszziomek.issuestracker.shared.domain.event;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueOpenedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private IssueType issueType;
    private String issueContent;
    private String issueName;

    public static IssueOpenedEventBuilder builder() {
        return new IssueOpenedEventBuilder();
    }

    private IssueOpenedEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            IssueType issueType,
            String issueContent,
            String issueName
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId =projectId;
        this.memberId = memberId;
        this.issueType = issueType;
        this.issueContent = issueContent;
        this.issueName = issueName;
    }

    public static class IssueOpenedEventBuilder
            extends EventBuilder<IssueOpenedEventBuilder, IssueOpenedEvent> {
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

        @NotBlank
        private String issueContent;

        @NotBlank
        private String issueName;

        public IssueOpenedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueOpenedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueOpenedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueOpenedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
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
                    issueId,
                    organizationId,
                    projectId,
                    memberId,
                    issueType,
                    issueContent,
                    issueName
            );
        }
    }
}
