package org.example.issuestracker.organizations.command.domain.invitation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.organizations.command.domain.member.Member;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Invitation {
    private final Member member;
}
