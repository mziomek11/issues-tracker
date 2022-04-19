package org.example.issuestracker.users.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.users.command.application.command.RegisterUserCommand;
import org.example.issuestracker.users.command.application.gateway.UserGateway;
import org.example.issuestracker.users.command.application.gateway.exception.UserEmailNotAvailableException;
import org.example.issuestracker.users.command.domain.account.User;
import org.example.issuestracker.users.command.domain.account.UserEmail;
import org.example.issuestracker.users.command.domain.account.UserPasswordHashingAlgorithm;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand> {
    private final EventSourcingHandler<User> eventSourcingHandler;
    private final UserGateway userGateway;
    private final UserPasswordHashingAlgorithm userPasswordHashingAlgorithm;

    /**
     * @throws UserEmailNotAvailableException see {@link UserGateway#ensureUserEmailIsAvailable(UserEmail)}
     */
    @Override
    public void handle(RegisterUserCommand command) {
        userGateway.ensureUserEmailIsAvailable(command.getUserEmail());

        var user = User.register(
            command.getUserId(),
            command.getUserEmail(),
            command.getUserPlainPassword(),
            userPasswordHashingAlgorithm
        );

        eventSourcingHandler.save(user);
    }
}
