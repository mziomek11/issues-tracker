package org.example.issuestracker.organizations.command.domain.invitation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.organizations.command.domain.member.MemberId;

@RequiredArgsConstructor
@Getter
public class InvitationNotFoundException extends IllegalStateException {
    private final transient MemberId memberId;
}
