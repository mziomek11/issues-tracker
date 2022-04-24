package com.mateuszziomek.issuestracker.users.command.domain.user.exception;

import com.mateuszziomek.issuestracker.users.command.domain.user.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserAlreadyActivatedException extends IllegalStateException {
    private final transient UserId userId;
}
