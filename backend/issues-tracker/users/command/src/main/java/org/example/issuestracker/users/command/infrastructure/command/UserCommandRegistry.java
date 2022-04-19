package org.example.issuestracker.users.command.infrastructure.command;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.users.command.application.command.ActivateUserCommand;
import org.example.issuestracker.users.command.application.command.RegisterUserCommand;
import org.example.issuestracker.users.command.application.command.handler.ActivateUserCommandHandler;
import org.example.issuestracker.users.command.application.command.handler.RegisterUserCommandHandler;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class UserCommandRegistry {
    private final CommandDispatcher commandDispatcher;
    private final RegisterUserCommandHandler registerUserCommandHandler;
    private final ActivateUserCommandHandler activateUserCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(RegisterUserCommand.class, registerUserCommandHandler);
        commandDispatcher.registerHandler(ActivateUserCommand.class, activateUserCommandHandler);
    }
}
