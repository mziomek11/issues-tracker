package com.mateuszziomek.issuestracker.shared.readmodel.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsOrganization {
    private UUID id;
    private UUID ownerId;
    private String name;
    private List<Member> members;
    private List<Project> projects;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Member {
        private UUID id;
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Project {
        private UUID id;
        private String name;
    }

    public boolean hasMemberWithId(UUID memberId) {
        return members.stream().map(Member::getId).anyMatch(id -> id.equals(memberId));
    }
}
