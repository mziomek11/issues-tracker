package org.example.issuestracker.accounts.command.domain.account;

import lombok.NoArgsConstructor;
import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.shared.domain.event.AccountOpenedEvent;
import org.example.issuestracker.shared.domain.valueobject.AccountStatus;

import static org.example.issuestracker.accounts.command.domain.EventFactory.*;

@NoArgsConstructor
public class Account extends AggregateRoot {
    private AccountId id;
    private AccountEmail email;
    private AccountHashedPassword password;
    private AccountStatus accountStatus;

    public static Account create(AccountId accountId, AccountEmail email, AccountHashedPassword password) {
        var account = new Account();

        account.raiseEvent(accountOpened(accountId, email, password));

        return account;
    }

    @Override
    public AccountId getId() {
        return id;
    }

    public void on(AccountOpenedEvent accountOpenedEvent) {
        id = AccountId.fromString(accountOpenedEvent.getId());
        email = new AccountEmail(accountOpenedEvent.getAccountEmail());
        password = new AccountHashedPassword(accountOpenedEvent.getAccountHashedPassword());
        accountStatus = AccountStatus.UNVERIFIED;
    }
}
