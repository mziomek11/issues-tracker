package org.example.issuestracker.accounts.command.infrastructure.gateway;

import org.example.issuestracker.accounts.command.application.gateway.AccountGateway;
import org.example.issuestracker.accounts.command.application.gateway.exception.AccountEmailAlreadyTakenException;
import org.example.issuestracker.accounts.command.domain.account.AccountEmail;
import org.springframework.stereotype.Service;

@Service
public class AccountGatewayImpl implements AccountGateway {
    /**
     * @throws AccountEmailAlreadyTakenException see {@link AccountGateway#ensureAccountEmailIsNotTaken(AccountEmail)}
     */
    @Override
    public void ensureAccountEmailIsNotTaken(AccountEmail accountEmail) {
        // @TODO implement
    }
}
