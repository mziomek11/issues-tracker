package org.example.issuestracker.accounts.command.application.gateway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.accounts.command.domain.account.AccountEmail;

@RequiredArgsConstructor
@Getter
public class AccountEmailAlreadyTakenException extends IllegalStateException {
    private final transient AccountEmail accountEmail;
}
