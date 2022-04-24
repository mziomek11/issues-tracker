package com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InvitationAlreadyPresentException extends IllegalStateException {
    private final transient MemberId memberId;
}
