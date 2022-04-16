package org.example.issuestracker.accounts.command.application.exception;

import lombok.RequiredArgsConstructor;
import org.example.issuestracker.accounts.command.domain.account.AccountEmail;

@RequiredArgsConstructor
public class AccountWithEmailAlreadyExistsException extends RuntimeException {
    private final transient AccountEmail accountEmail;
}
