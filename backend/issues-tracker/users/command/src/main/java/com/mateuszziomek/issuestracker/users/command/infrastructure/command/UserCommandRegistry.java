package com.mateuszziomek.issuestracker.users.command.infrastructure.command;

import com.mateuszziomek.issuestracker.users.command.application.command.ActivateUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.command.handler.ActivateUserCommandHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.dispatcher.CommandDispatcher;
import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.command.handler.RegisterUserCommandHandler;
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
