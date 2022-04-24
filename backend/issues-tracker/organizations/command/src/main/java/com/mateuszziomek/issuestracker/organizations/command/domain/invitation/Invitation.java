package com.mateuszziomek.issuestracker.organizations.command.domain.invitation;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Invitation {
    private final Member member;
}
