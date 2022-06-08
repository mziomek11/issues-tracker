package com.mateuszziomek.issuestracker.organizations.command.application.service.exception;

import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberNotFoundException extends RuntimeException {
    private final transient MemberEmail memberEmail;
}
