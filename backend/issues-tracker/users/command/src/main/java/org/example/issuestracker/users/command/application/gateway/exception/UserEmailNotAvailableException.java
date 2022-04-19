package org.example.issuestracker.users.command.application.gateway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.users.command.domain.account.UserEmail;

@RequiredArgsConstructor
@Getter
public class UserEmailNotAvailableException extends IllegalStateException {
    private final transient UserEmail userEmail;
}
