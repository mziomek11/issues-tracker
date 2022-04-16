package org.example.issuestracker.accounts.command.domain;

import org.example.issuestracker.accounts.command.domain.account.AccountActivationToken;
import org.example.issuestracker.accounts.command.domain.account.AccountEmail;
import org.example.issuestracker.accounts.command.domain.account.AccountHashedPassword;
import org.example.issuestracker.accounts.command.domain.account.AccountId;
import org.example.issuestracker.shared.domain.event.AccountActivatedEvent;
import org.example.issuestracker.shared.domain.event.AccountOpenedEvent;

public class EventFactory {
    private EventFactory() {}

    public static AccountOpenedEvent accountOpened(
            AccountId id,
            AccountEmail email,
            AccountHashedPassword password,
            AccountActivationToken activationToken
    ) {
        return AccountOpenedEvent
                .builder()
                .accountId(id.getValue())
                .accountEmail(email.text())
                .accountHashedPassword(password.text())
                .accountActivationToken(activationToken.value())
                .build();
    }

    public static AccountActivatedEvent accountActivated(AccountId id) {
        return AccountActivatedEvent
                .builder()
                .accountId(id.getValue())
                .build();
    }
}
