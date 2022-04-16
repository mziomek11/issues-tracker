package org.example.issuestracker.accounts.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.accounts.command.application.command.OpenAccountCommand;
import org.example.issuestracker.accounts.command.application.exception.AccountWithEmailAlreadyExistsException;
import org.example.issuestracker.accounts.command.application.gateway.AccountGateway;
import org.example.issuestracker.accounts.command.domain.account.Account;
import org.example.issuestracker.accounts.command.domain.account.AccountPasswordHashingAlgorithm;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAccountCommandHandler implements CommandHandler<OpenAccountCommand> {
    private final EventSourcingHandler<Account> eventSourcingHandler;
    private final AccountGateway accountGateway;
    private final AccountPasswordHashingAlgorithm accountPasswordHashingAlgorithm;

    /**
     * @throws AccountWithEmailAlreadyExistsException if account with given email already exists
     */
    @Override
    public void handle(OpenAccountCommand command) {
        if (accountGateway.accountWithEmailExists(command.getAccountEmail())) {
            throw new AccountWithEmailAlreadyExistsException(command.getAccountEmail());
        }

        var account = Account.open(
            command.getAccountId(),
            command.getAccountEmail(),
            command.getAccountPlainPassword(),
            accountPasswordHashingAlgorithm
        );

        eventSourcingHandler.save(account);
    }
}
