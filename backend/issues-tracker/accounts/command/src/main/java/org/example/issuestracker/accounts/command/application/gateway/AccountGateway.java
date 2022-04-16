package org.example.issuestracker.accounts.command.application.gateway;

import org.example.issuestracker.accounts.command.domain.account.AccountEmail;

public interface AccountGateway {
    boolean accountWithEmailExists(AccountEmail accountEmail);
}
