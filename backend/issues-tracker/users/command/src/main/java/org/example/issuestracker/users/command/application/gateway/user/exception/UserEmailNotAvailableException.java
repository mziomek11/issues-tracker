package org.example.issuestracker.users.command.application.gateway.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.users.command.domain.user.UserEmail;

@RequiredArgsConstructor
@Getter
public class UserEmailNotAvailableException extends IllegalStateException {
    private final transient UserEmail userEmail;
}
