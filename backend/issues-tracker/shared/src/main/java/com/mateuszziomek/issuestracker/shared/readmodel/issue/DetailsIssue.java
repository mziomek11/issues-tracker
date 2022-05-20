package com.mateuszziomek.issuestracker.shared.readmodel.issue;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueStatus;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueUpdateType;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;
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
        private T previousValues;
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
}
