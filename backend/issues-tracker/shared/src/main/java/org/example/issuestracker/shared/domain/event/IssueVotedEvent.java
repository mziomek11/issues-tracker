package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;
import org.example.issuestracker.shared.domain.valueobject.VoteType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueVotedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private VoteType voteType;

    public static IssueVotedEventBuilder builder() {
        return new IssueVotedEventBuilder();
    }

    private IssueVotedEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            VoteType voteType
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId = projectId;
        this.memberId = memberId;
        this.voteType = voteType;
    }

    public static class IssueVotedEventBuilder
            extends EventBuilder<IssueVotedEventBuilder, IssueVotedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID memberId;

        @NotNull
        private VoteType voteType;

        public IssueVotedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueVotedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueVotedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueVotedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        public IssueVotedEventBuilder voteType(VoteType voteType) {
            this.voteType = voteType;
            return this;
        }

        @Override
        protected IssueVotedEvent create() {
            return new IssueVotedEvent(
                    issueId,
                    organizationId,
                    projectId,
                    memberId,
                    voteType
            );
        }
    }
}
