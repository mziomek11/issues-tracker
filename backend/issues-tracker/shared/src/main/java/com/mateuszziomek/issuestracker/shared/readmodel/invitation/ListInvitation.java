package com.mateuszziomek.issuestracker.shared.readmodel.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListInvitation {
    private Member member;
    private Organization organization;

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
    public static class Organization {
        private UUID id;
        private String name;
    }
}
