package org.example.issuestracker.organizations.command.domain.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class Member {
    private final MemberId id;

    public Member(MemberId memberId) {
        this.id = Objects.requireNonNull(memberId);
    }
}
