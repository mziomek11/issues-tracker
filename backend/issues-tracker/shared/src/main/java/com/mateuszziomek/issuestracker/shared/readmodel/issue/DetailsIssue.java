package com.mateuszziomek.issuestracker.shared.readmodel.issue;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsIssue {
    private UUID id;
    private String name;
    private IssueStatus status;
    private IssueType type;
    private List<DetailsIssue.Vote> votes;
    private List<DetailsIssue.IssueUpdate> updates;
    private List<DetailsIssue.Comment> comments;
    private DetailsIssue.Creator creator;
    private DetailsIssue.Project project;
    private DetailsIssue.Organization organization;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IssueUpdate<T> {
        private IssueUpdateType type;
        private LocalDateTime updatedAt;
        private T previousValue;
        private T currentValue;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Vote {
        private UUID memberId;
        private VoteType type;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Creator {
        private UUID id;
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Project {
        private UUID id;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Organization {
        private UUID id;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Comment {
        private UUID id;
        private String content;
        private CommentStatus status;
        private DetailsIssue.Creator creator;
        private List<DetailsIssue.CommentUpdate> updates;
        private List<DetailsIssue.Vote> votes;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentUpdate<T> {
        private CommentUpdateType type;
        private LocalDateTime updatedAt;
        private T previousValues;
        private T currentValue;
    }
}
