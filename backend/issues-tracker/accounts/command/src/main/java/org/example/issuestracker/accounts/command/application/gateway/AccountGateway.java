package org.example.issuestracker.accounts.command.application.gateway;

import org.example.issuestracker.accounts.command.application.gateway.exception.AccountEmailAlreadyTakenException;
import org.example.issuestracker.accounts.command.domain.account.AccountEmail;

public interface AccountGateway {
    /**
     * @throws AccountEmailAlreadyTakenException if account with given email is already taken
     */
    boolean ensureAccountEmailIsNotTaken(AccountEmail accountEmail);
}
