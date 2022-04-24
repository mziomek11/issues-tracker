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
public class IssueCommentContentChangedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private UUID commentId;
    private String commentContent;

    public static IssueCommentContentChangedEventBuilder builder() {
        return new IssueCommentContentChangedEventBuilder();
    }

    private IssueCommentContentChangedEvent(
            UUID issueId,
            UUID organizationId,
            UUID projectId,
            UUID memberId,
            UUID commentId,
            String commentContent
    ) {
        super(issueId);

        this.organizationId = organizationId;
        this.projectId = projectId;
        this.memberId = memberId;
        this.commentId = commentId;
        this.commentContent = commentContent;
    }

    public static class IssueCommentContentChangedEventBuilder
            extends EventBuilder<IssueCommentContentChangedEventBuilder, IssueCommentContentChangedEvent> {
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

        @NotBlank
        private String commentContent;

        public IssueCommentContentChangedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentContentChangedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueCommentContentChangedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueCommentContentChangedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        public IssueCommentContentChangedEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public IssueCommentContentChangedEventBuilder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        @Override
        protected IssueCommentContentChangedEvent create() {
            return new IssueCommentContentChangedEvent(
                    issueId,
                    organizationId,
                    projectId,
                    memberId,
                    commentId,
                    commentContent
            );
        }
    }
}
