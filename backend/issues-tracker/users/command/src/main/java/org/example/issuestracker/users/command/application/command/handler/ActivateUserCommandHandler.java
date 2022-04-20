package org.example.issuestracker.users.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.users.command.application.command.ActivateUserCommand;
import org.example.issuestracker.users.command.domain.user.User;
import org.example.issuestracker.users.command.domain.user.UserActivationToken;
import org.example.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import org.example.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
import org.example.issuestracker.users.command.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivateUserCommandHandler implements CommandHandler<ActivateUserCommand> {
    private final EventSourcingHandler<User> eventSourcingHandler;

    /**
     * @throws UserActivationTokenMismatchException see {@link User#activate(UserActivationToken)}
     * @throws UserAlreadyActivatedException see {@link User#activate(UserActivationToken)}
     * @throws UserNotFoundException if user with given id does not exist
     */
    @Override
    public void handle(ActivateUserCommand command) {
        var user = eventSourcingHandler
                .getById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException(command.getUserId()));

        user.activate(command.getUserActivationToken());

        eventSourcingHandler.save(user);
    }
}
