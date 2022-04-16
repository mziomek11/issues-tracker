package org.example.issuestracker.accounts.command.domain.account;

import lombok.NoArgsConstructor;
import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountActivationTokenMismatchException;
import org.example.issuestracker.accounts.command.domain.account.exception.AccountAlreadyActivatedException;
import org.example.issuestracker.shared.domain.event.AccountActivatedEvent;
import org.example.issuestracker.shared.domain.event.AccountOpenedEvent;
import org.example.issuestracker.shared.domain.valueobject.AccountStatus;

import java.util.Optional;

import static org.example.issuestracker.accounts.command.domain.EventFactory.*;

@NoArgsConstructor
public class Account extends AggregateRoot {
    private AccountId id;
    private AccountEmail email;
    private AccountHashedPassword password;
    private AccountStatus status;
    private Optional<AccountActivationToken> activationToken;

    public static Account create(AccountId accountId, AccountEmail email, AccountHashedPassword password) {
        var account = new Account();

        account.raiseEvent(accountOpened(accountId, email, password, AccountActivationToken.generate()));

        return account;
    }

    /**
     * @param token that activates account
     * @throws AccountActivationTokenMismatchException if account can't be activated with given token
     * @throws AccountAlreadyActivatedException if account is already activated
     */
    public void activate(AccountActivationToken token) {
        if (isActivated()) {
            throw new AccountAlreadyActivatedException(id);
        }

        if (activationToken.isEmpty() || !activationToken.get().equals(token)) {
            throw new AccountActivationTokenMismatchException(id, token);
        }

        raiseEvent(accountActivated(id));
    }

    private boolean isActivated() {
        return this.status.equals(AccountStatus.ACTIVATED);
    }

    @Override
    public AccountId getId() {
        return id;
    }

    public void on(AccountOpenedEvent accountOpenedEvent) {
        id = AccountId.fromString(accountOpenedEvent.getId());
        email = new AccountEmail(accountOpenedEvent.getAccountEmail());
        password = new AccountHashedPassword(accountOpenedEvent.getAccountHashedPassword());
        status = AccountStatus.UNVERIFIED;
        activationToken = Optional.of(AccountActivationToken.fromString(accountOpenedEvent.getAccountActivationToken()));
    }

    public void on(AccountActivatedEvent accountActivatedEvent) {
        status = AccountStatus.ACTIVATED;
        activationToken = Optional.empty();
    }
}
