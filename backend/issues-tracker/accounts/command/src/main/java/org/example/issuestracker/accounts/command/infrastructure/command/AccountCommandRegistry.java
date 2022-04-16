package org.example.issuestracker.accounts.command.infrastructure.command;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.accounts.command.application.command.ActivateAccountCommand;
import org.example.issuestracker.accounts.command.application.command.OpenAccountCommand;
import org.example.issuestracker.accounts.command.application.command.handler.ActivateAccountCommandHandler;
import org.example.issuestracker.accounts.command.application.command.handler.OpenAccountCommandHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class AccountCommandRegistry {
    private final CommandDispatcher commandDispatcher;
    private final OpenAccountCommandHandler openAccountCommandHandler;
    private final ActivateAccountCommandHandler activateAccountCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, openAccountCommandHandler);
        commandDispatcher.registerHandler(ActivateAccountCommand.class, activateAccountCommandHandler);
    }
}
