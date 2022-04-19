package org.example.issuestracker.users.command.domain.account.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.users.command.domain.account.UserActivationToken;
import org.example.issuestracker.users.command.domain.account.UserId;

@RequiredArgsConstructor
@Getter
public class UserActivationTokenMismatchException extends IllegalStateException {
    private final transient UserId userId;
    private final transient UserActivationToken userActivationToken;
}
