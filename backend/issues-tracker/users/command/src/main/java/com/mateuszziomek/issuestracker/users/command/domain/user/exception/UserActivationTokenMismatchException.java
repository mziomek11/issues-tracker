package com.mateuszziomek.issuestracker.users.command.domain.user.exception;

import com.mateuszziomek.issuestracker.users.command.domain.user.UserActivationToken;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserActivationTokenMismatchException extends IllegalStateException {
    private final transient UserId userId;
    private final transient UserActivationToken userActivationToken;
}
