package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.users.command.application.command.ActivateUserCommand;
import com.mateuszziomek.issuestracker.users.command.domain.user.User;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserActivationToken;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
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
