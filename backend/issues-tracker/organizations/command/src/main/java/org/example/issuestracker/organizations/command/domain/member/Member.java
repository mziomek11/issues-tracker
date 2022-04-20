package org.example.issuestracker.organizations.command.domain.member;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Member {
    private final MemberId memberId;

    public Member(MemberId memberId) {
        this.memberId = Objects.requireNonNull(memberId);
    }
}
