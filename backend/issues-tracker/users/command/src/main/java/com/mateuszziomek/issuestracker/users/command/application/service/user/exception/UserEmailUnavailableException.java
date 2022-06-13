package com.mateuszziomek.issuestracker.users.command.application.service.user.exception;

import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserEmailUnavailableException extends IllegalStateException {
    private final transient UserEmail userEmail;
}
