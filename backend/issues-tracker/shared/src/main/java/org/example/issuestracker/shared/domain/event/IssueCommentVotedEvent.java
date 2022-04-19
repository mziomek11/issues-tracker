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
public class IssueCommentVotedEvent extends BaseEvent {
    private UUID commentId;
    private UUID voterId;
    private VoteType voteType;

    public static IssueCommentVotedEventBuilder builder() {
        return new IssueCommentVotedEventBuilder();
    }

    private IssueCommentVotedEvent(UUID issueId, UUID commentId, UUID voterId, VoteType voteType) {
        super(issueId);

        this.commentId = commentId;
        this.voterId = voterId;
        this.voteType = voteType;
    }

    public static class IssueCommentVotedEventBuilder
            extends EventBuilder<IssueCommentVotedEventBuilder, IssueCommentVotedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID commentId;

        @NotNull
        private UUID voterId;

        @NotNull
        private VoteType voteType;

        public IssueCommentVotedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentVotedEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public IssueCommentVotedEventBuilder voterId(UUID voterId) {
            this.voterId = voterId;
            return this;
        }

        public IssueCommentVotedEventBuilder voteType(VoteType voteType) {
            this.voteType = voteType;
            return this;
        }

        @Override
        protected IssueCommentVotedEvent create() {
            return new IssueCommentVotedEvent(
                    issueId,
                    commentId,
                    voterId,
                    voteType
            );
        }
    }
}
