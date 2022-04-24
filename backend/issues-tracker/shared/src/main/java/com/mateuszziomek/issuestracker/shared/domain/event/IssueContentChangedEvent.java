package com.mateuszziomek.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueContentChangedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private String issueContent;

    public static IssueCommentVotedEventBuilder builder() {
        return new IssueCommentVotedEventBuilder();
    }

    private IssueContentChangedEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            String issueContent
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId = projectId;
        this.memberId = memberId;
        this.issueContent = issueContent;
    }

    public static class IssueCommentVotedEventBuilder
            extends EventBuilder<IssueCommentVotedEventBuilder, IssueContentChangedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID memberId;

        @NotBlank
        private String issueContent;

        public IssueCommentVotedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueCommentVotedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueCommentVotedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

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
                    issueId,
                    organizationId,
                    projectId,
                    memberId,
                    issueContent
            );
        }
    }
}
