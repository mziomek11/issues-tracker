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
public class IssueCommentedEvent extends BaseEvent {
    private UUID organizationId;
    private UUID projectId;
    private UUID memberId;
    private UUID commentId;
    private String commentContent;

    public static IssueCommentedEventBuilder builder() {
        return new IssueCommentedEventBuilder();
    }

    private IssueCommentedEvent(
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

    public static class IssueCommentedEventBuilder
            extends EventBuilder<IssueCommentedEventBuilder, IssueCommentedEvent> {
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

        public IssueCommentedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public IssueCommentedEventBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public IssueCommentedEventBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public IssueCommentedEventBuilder memberId(UUID memberId) {
            this.memberId = memberId;
            return this;
        }

        public IssueCommentedEventBuilder commentId(UUID commentId) {
            this.commentId = commentId;
            return this;
        }

        public IssueCommentedEventBuilder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        @Override
        protected IssueCommentedEvent create() {
            return new IssueCommentedEvent(
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
