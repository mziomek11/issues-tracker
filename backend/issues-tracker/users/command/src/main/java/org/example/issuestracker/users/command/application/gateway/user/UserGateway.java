package org.example.issuestracker.users.command.application.gateway.user;

import org.example.issuestracker.users.command.application.gateway.user.exception.UserEmailNotAvailableException;
import org.example.issuestracker.users.command.domain.user.UserEmail;

public interface UserGateway {
    /**
     * @throws UserEmailNotAvailableException if account with given email is already taken
     */
    void ensureUserEmailIsAvailable(UserEmail userEmail);
}
