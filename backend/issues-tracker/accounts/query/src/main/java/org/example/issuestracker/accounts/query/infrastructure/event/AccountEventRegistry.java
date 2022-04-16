package org.example.issuestracker.accounts.query.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.dispatcher.EventDispatcher;
import org.example.issuestracker.accounts.query.application.event.handler.AccountOpenedEventHandler;
import org.example.issuestracker.shared.domain.event.AccountOpenedEvent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class AccountEventRegistry {
    private final EventDispatcher eventDispatcher;
    private final AccountOpenedEventHandler accountOpenedEventHandler;

    @PostConstruct
    public void registerHandlers() {
        eventDispatcher.registerHandler(AccountOpenedEvent.class, accountOpenedEventHandler);
    }
}
