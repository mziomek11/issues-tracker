package com.mateuszziomek.issuestracker.shared.readmodel.issue;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueStatus;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListIssue {
    private UUID id;
    private String name;
    private IssueStatus status;
    private IssueType type;
    private List<Vote> votes;
    private Creator creator;
    private Project project;
    private Organization organization;

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
