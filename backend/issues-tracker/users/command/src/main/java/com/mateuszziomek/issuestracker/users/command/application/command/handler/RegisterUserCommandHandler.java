package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.users.command.application.service.user.UserService;
import com.mateuszziomek.issuestracker.users.command.application.service.user.exception.UserEmailUnavailableException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand;
import com.mateuszziomek.issuestracker.users.command.domain.user.User;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPasswordHashingAlgorithm;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand> {
    private final EventSourcingHandler<User> eventSourcingHandler;
    private final UserService userService;
    private final UserPasswordHashingAlgorithm userPasswordHashingAlgorithm;

    /**
     * @throws UserEmailUnavailableException see {@link UserService#ensureUserEmailIsAvailable(UserEmail)}
     */
    @Override
    public void handle(RegisterUserCommand command) {
        userService.ensureUserEmailIsAvailable(command.getUserEmail());

        var user = User.register(
            command.getUserId(),
            command.getUserEmail(),
            command.getUserPlainPassword(),
            userPasswordHashingAlgorithm
        );

        eventSourcingHandler.save(user);
    }
}
