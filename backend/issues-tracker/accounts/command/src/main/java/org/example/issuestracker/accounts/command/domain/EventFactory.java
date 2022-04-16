package org.example.issuestracker.accounts.command.domain;

import org.example.issuestracker.accounts.command.domain.account.AccountEmail;
import org.example.issuestracker.accounts.command.domain.account.AccountHashedPassword;
import org.example.issuestracker.accounts.command.domain.account.AccountId;
import org.example.issuestracker.shared.domain.event.AccountOpenedEvent;

public class EventFactory {
    private EventFactory() {}

    public static AccountOpenedEvent accountOpened(
            AccountId id,
            AccountEmail email,
            AccountHashedPassword password
    ) {
        return AccountOpenedEvent
                .builder()
                .accountId(id.getValue())
                .accountEmail(email.text())
                .accountHashedPassword(password.text())
                .build();
    }
}
