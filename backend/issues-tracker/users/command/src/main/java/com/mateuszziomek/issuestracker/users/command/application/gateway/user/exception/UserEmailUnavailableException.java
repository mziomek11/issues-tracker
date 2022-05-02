package com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;

@RequiredArgsConstructor
@Getter
public class UserEmailUnavailableException extends IllegalStateException {
    private final transient UserEmail userEmail;
}
