package org.example.issuestracker.users.command.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.users.command.domain.user.UserActivationToken;
import org.example.issuestracker.users.command.domain.user.UserId;

@RequiredArgsConstructor
@Getter
public class UserActivationTokenMismatchException extends IllegalStateException {
    private final transient UserId userId;
    private final transient UserActivationToken userActivationToken;
}
