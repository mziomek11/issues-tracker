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
    private UUID voterId;
    private VoteType voteType;

    public static IssueVotedEventBuilder builder() {
        return new IssueVotedEventBuilder();
    }

    private IssueVotedEvent(UUID issueId, UUID voterId, VoteType voteType) {
        super(issueId);

        this.voterId = voterId;
        this.voteType = voteType;
    }

    public static class IssueVotedEventBuilder
            extends EventBuilder<IssueVotedEventBuilder, IssueVotedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID voterId;

        @NotNull
        private VoteType voteType;

        public IssueVotedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueVotedEventBuilder voterId(UUID voterId) {
            this.voterId = voterId;
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
                    voterId,
                    voteType
            );
        }
    }
}
