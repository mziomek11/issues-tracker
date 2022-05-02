package com.mateuszziomek.issuestracker.users.command.application.gateway.user;

import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;

public interface UserGateway {
    /**
     * @throws UserEmailUnavailableException if account with given email is already taken
     * @throws UserServiceUnavailableException if user service in unavailable
     */
    void ensureUserEmailIsAvailable(UserEmail userEmail);
}
