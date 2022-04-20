package org.example.issuestracker.organizations.command.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.organizations.command.domain.member.MemberId;

@RequiredArgsConstructor
@Getter
public class MemberAlreadyPresentException extends IllegalStateException {
    private final transient MemberId memberId;
}
