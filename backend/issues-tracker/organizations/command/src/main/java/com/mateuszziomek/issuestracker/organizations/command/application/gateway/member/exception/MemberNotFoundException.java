package com.mateuszziomek.issuestracker.organizations.command.application.gateway.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;

@RequiredArgsConstructor
@Getter
public class MemberNotFoundException extends RuntimeException {
    private final transient MemberEmail memberEmail;
}
