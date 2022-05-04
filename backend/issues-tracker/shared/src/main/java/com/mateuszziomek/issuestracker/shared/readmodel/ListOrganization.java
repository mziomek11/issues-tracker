package com.mateuszziomek.issuestracker.shared.readmodel;

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
public class ListOrganization {
    private UUID id;
    private UUID ownerId;
    private List<ListOrganization.Member> members;
    private List<ListOrganization.Project> projects;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Member {
        private UUID id;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Project {
        private UUID id;
        private String name;
    }
}
