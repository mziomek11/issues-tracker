package org.example.issuestracker.issues.common.event;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.Objects;
import java.util.UUID;

@Getter
public class IssueCommentVotedEvent extends BaseEvent {
    private final String commentId;
    private final String voterId;
    private final VoteType voteType;

    public static IssueCommentVotedEventBuilder builder() {
        return new IssueCommentVotedEventBuilder();
    }

    private IssueCommentVotedEvent(String issueId, String commentId, String voterId, VoteType voteType) {
        super(issueId);

        this.commentId = Objects.requireNonNull(commentId);
        this.voterId = Objects.requireNonNull(voterId);
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
                    issueId.toString(),
                    commentId.toString(),
                    voterId.toString(),
                    voteType
            );
        }
    }
}
