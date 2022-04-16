package org.example.issuestracker.accounts.command.domain.account.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.accounts.command.domain.account.AccountId;

@RequiredArgsConstructor
@Getter
public class AccountNotFoundException extends RuntimeException {
    private final AccountId accountId;
}
