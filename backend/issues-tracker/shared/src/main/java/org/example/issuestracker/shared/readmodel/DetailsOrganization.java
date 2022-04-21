package org.example.issuestracker.shared.readmodel;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DetailsOrganization {
    private UUID id;
    private UUID ownerId;
    private List<Member> members;
    private List<Project> projects;

    @Getter
    @Builder
    public static class Member {
        private UUID id;
    }

    @Getter
    @Builder
    public static class Project {
        private UUID id;
        private String name;
    }
}
