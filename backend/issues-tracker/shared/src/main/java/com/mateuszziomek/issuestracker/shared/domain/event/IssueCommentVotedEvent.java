package com.mateuszziomek.issuestracker.shared.domain.event;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueCommentVotedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private UUID commentId;
    private VoteType voteType;

    public static IssueCommentVotedEventBuilder builder() {
        return new IssueCommentVotedEventBuilder();
    }

    private IssueCommentVotedEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            UUID commentId,
            VoteType voteType
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId = projectId;
        this.memberId = memberId;
        this.commentId = commentId;
        this.voteType = voteType;
    }

    public static class IssueCommentVotedEventBuilder
            extends EventBuilder<IssueCommentVotedEventBuilder, IssueCommentVotedEvent> {
        @NotNull
        private UUID issueId;

        @NotNull
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID memberId;

        @NotNull
        private UUID commentId;

        @NotNull
        private VoteType voteType;

        public IssueCommentVotedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

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


        public IssueCommentVotedEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
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
                    organizationId,
                    projectId,
                    memberId,
                    commentId,
                    voteType
            );
        }
    }
}
