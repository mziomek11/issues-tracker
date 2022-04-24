package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailNotAvailableException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.UserGateway;
import com.mateuszziomek.issuestracker.users.command.domain.user.User;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPasswordHashingAlgorithm;
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
