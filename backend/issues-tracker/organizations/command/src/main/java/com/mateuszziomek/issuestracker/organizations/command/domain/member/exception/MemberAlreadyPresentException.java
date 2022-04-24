package com.mateuszziomek.issuestracker.organizations.command.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;

@RequiredArgsConstructor
@Getter
public class MemberAlreadyPresentException extends IllegalStateException {
    private final transient MemberId memberId;
}
