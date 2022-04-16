package org.example.issuestracker.accounts.command.domain.account.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.accounts.command.domain.account.AccountActivationToken;
import org.example.issuestracker.accounts.command.domain.account.AccountId;

@RequiredArgsConstructor
@Getter
public class AccountActivationTokenMismatchException extends IllegalStateException {
    private final transient AccountId accountId;
    private final transient AccountActivationToken accountActivationToken;
}
