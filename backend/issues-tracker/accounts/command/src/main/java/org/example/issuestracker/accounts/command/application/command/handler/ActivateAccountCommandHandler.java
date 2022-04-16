package org.example.issuestracker.accounts.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.accounts.command.application.command.ActivateAccountCommand;
import org.example.issuestracker.accounts.command.domain.account.Account;
import org.example.issuestracker.accounts.command.domain.account.AccountActivationToken;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountActivationTokenMismatchException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountAlreadyActivatedException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivateAccountCommandHandler implements CommandHandler<ActivateAccountCommand> {
    private final EventSourcingHandler<Account> eventSourcingHandler;

    /**
     * @throws AccountActivationTokenMismatchException see {@link Account#activate(AccountActivationToken)}
     * @throws AccountAlreadyActivatedException see {@link Account#activate(AccountActivationToken)}
     * @throws AccountNotFoundException if issue with given id does not exist
     */
    @Override
    public void handle(ActivateAccountCommand command) {
        var account = eventSourcingHandler
                .getById(command.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(command.getAccountId()));

        account.activate(command.getAccountActivationToken());

        eventSourcingHandler.save(account);
    }
}
