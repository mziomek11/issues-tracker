package org.example.issuestracker.accounts.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.EventHandler;
import org.example.issuestracker.shared.domain.event.AccountOpenedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountOpenedEventHandler implements EventHandler<AccountOpenedEvent> {
    @Override
    public void handle(AccountOpenedEvent event) {
        System.out.println("HANDLE AccountOpenedEvent FROM HANDLER");
    }
}
