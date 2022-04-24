package com.mateuszziomek.issuestracker.users.command.application.gateway.user;

import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailNotAvailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;

public interface UserGateway {
    /**
     * @throws UserEmailNotAvailableException if account with given email is already taken
     */
    void ensureUserEmailIsAvailable(UserEmail userEmail);
}
