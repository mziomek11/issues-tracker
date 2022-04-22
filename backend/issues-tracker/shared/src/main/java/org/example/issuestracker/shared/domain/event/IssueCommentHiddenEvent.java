package org.example.issuestracker.shared.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class IssueCommentHiddenEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private UUID commentId;

    public static IssueCommentHiddenEventBuilder builder() {
        return new IssueCommentHiddenEventBuilder();
    }

    private IssueCommentHiddenEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            UUID commentId
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId = projectId;
        this.memberId = memberId;
        this.commentId = commentId;
    }

    public static class IssueCommentHiddenEventBuilder
            extends EventBuilder<IssueCommentHiddenEventBuilder, IssueCommentHiddenEvent> {
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

        public IssueCommentHiddenEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentHiddenEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueCommentHiddenEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueCommentHiddenEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        public IssueCommentHiddenEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        @Override
        protected IssueCommentHiddenEvent create() {
            return new IssueCommentHiddenEvent(
                    issueId,
                    organizationId,
                    projectId,
                    memberId,
                    commentId
            );
        }
    }
}
