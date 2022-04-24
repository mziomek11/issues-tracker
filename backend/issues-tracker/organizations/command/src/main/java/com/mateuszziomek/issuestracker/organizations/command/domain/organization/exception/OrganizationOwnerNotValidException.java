package com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrganizationOwnerNotValidException extends IllegalStateException{
    private final transient MemberId memberId;
}
